package com.example.dropchat.domainLayer.remote

import androidx.core.net.toUri
import com.example.dropchat.dataLayer.remote.GroupMessage
import com.example.dropchat.dataLayer.remote.GroupProfile
import com.example.dropchat.dataLayer.remote.Message
import com.example.dropchat.dataLayer.remote.Messages
import com.example.dropchat.dataLayer.remote.Profile
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import io.grpc.Context.Storage
import kotlinx.coroutines.tasks.await

class ServerRepoImpl : ServerRepo {

   private val db = Firebase.firestore
    private val storageRef = Firebase.storage.reference


    override suspend fun sendProfile(profile : Profile){

       db.collection("Profiles")
           .document(profile.userMail)
           .set(profile)


        storageRef.child("Images/${profile.userMail}")
            .putFile(profile.userImage.toUri())
            .await()

   }

    override suspend fun getProfiles(): List<Profile> {

       return db.collection("Profiles")
            .get()
            .await()
            .toObjects(Profile::class.java)
    }

    override suspend fun sendMessage(uniqueId: String, uniqueIdReverse : String,message: Message) {

        val doc = db.collection("Chats")
                    .document(uniqueId)
                    .get()
                    .await()

        val doc2 = db.collection("Chats")
            .document(uniqueIdReverse)
            .get()
            .await()


        if(doc.exists()){
            db.collection("Chats")
                .document(uniqueId)
                .update("Messages", FieldValue.arrayUnion(message))
        }

        else if(doc2.exists()){
            db.collection("Chats")
                .document(uniqueId)
                .update("Messages", FieldValue.arrayUnion(message))
        }

        else{
            db.collection("Chats")
                .document(uniqueId)
                .set(hashMapOf("Messages" to message))
        }
    }

    override suspend fun getMessages(uniqueId: String, uniqueIdReverse : String): Messages? {

        val doc = db.collection("Chats")
            .document(uniqueId)
            .get()
            .await()

        val doc2 = db.collection("Chats")
            .document(uniqueIdReverse)
            .get()
            .await()

        if(doc.exists()){
            return db.collection("Chats")
                .document(uniqueId)
                .get()
                .await()
                .toObject(Messages::class.java)!!
        }

        else if(doc2.exists()){
            return db.collection("Chats")
                .document(uniqueId)
                .get()
                .await()
                .toObject(Messages()::class.java)!!
        }

        else{
            return null
        }


    }

    override suspend fun sendGroupInfo(groupProfile: GroupProfile) {

        db.collection("Profiles")
            .document(groupProfile.groupName)
            .set(groupProfile)

        storageRef.child("Images/${groupProfile.groupName}")
            .putFile(groupProfile.groupDisplay.toUri())
            .await()
    }

    override suspend fun getGroupInfo(groupName: String): GroupProfile? {

        return db.collection("Profiles")
            .document(groupName)
            .get()
            .await()
            .toObject(GroupProfile::class.java)
    }

    override suspend fun sendGroupMessage(groupName: String, groupMessage: GroupMessage) {

        val doc = db.collection("Chats")
                    .document(groupName)
                    .get()
                    .await()

        if(doc.exists()){
            db.collection("Chats")
                .document(groupName)
                .update("groupMessages", FieldValue.arrayUnion(groupMessage))
        }

        else{
            db.collection("Chats")
                .document(groupName)
                .set(hashMapOf("groupMessages" to groupMessage))
        }

    }

    override suspend fun getGroupMessages(groupName: String): GroupMessage? {
        val doc = db.collection("Chats")
            .document(groupName)
            .get()
            .await()

        if(doc.exists()){
            return db.collection("Chats")
                .document(groupName)
                .get()
                .await()
                .toObject(GroupMessage::class.java)
        }

        else{
            return null
        }
    }
}
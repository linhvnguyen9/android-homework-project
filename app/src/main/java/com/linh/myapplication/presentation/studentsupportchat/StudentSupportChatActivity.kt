package com.linh.myapplication.presentation.studentsupportchat

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.linh.myapplication.databinding.ActivityStudentSupportChatBinding
import com.linh.myapplication.domain.Chat
import com.linh.myapplication.domain.ChatMessage
import timber.log.Timber

class StudentSupportChatActivity : AppCompatActivity() {
    private val studentUid : String? by lazy { intent.getStringExtra(EXTRA_STUDENT_UID) }

    private lateinit var db: FirebaseDatabase
    private lateinit var binding: ActivityStudentSupportChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentSupportChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        db = FirebaseDatabase.getInstance()
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        val messagesRef = db.reference.child(MESSAGES_CHILD).child(studentUid ?: user?.uid ?: "")

        val options = FirebaseRecyclerOptions.Builder<ChatMessage>()
            .setQuery(messagesRef, ChatMessage::class.java)
            .build()

        Timber.d(options.snapshots.toString())

        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true

        val adapter = StudentSupportChatAdapter(options, ANONYMOUS)

        binding.recyclerStudentSupportChat.layoutManager = layoutManager
        binding.recyclerStudentSupportChat.adapter = adapter

        adapter.
        registerAdapterDataObserver(
            ScrollToBottomObserver(
                binding.recyclerStudentSupportChat, adapter,
                layoutManager
            )
        )

        val currentUser = FirebaseAuth.getInstance().currentUser?.displayName
        val currentUserAvatar = FirebaseAuth.getInstance().currentUser?.photoUrl.toString()

        binding.buttonStudentSupportChatSend.setOnClickListener {
            val messageContent = binding.textStudentSupportChatMessage.text.toString()
            val message = ChatMessage(messageContent, currentUser ?: ANONYMOUS)

            db.reference.child(MESSAGES_CHILD).child(studentUid ?: user?.uid ?: "").push().setValue(message)

            if (studentUid != null) {
                db.reference.child(CHATS_CHILD).child(studentUid ?: "").child("lastMessage").setValue(messageContent)
            } else {
                val chat = Chat(currentUser, currentUserAvatar, messageContent, user?.uid ?: "")
                db.reference.child(CHATS_CHILD).child(user?.uid ?: "").setValue(chat)
            }
            binding.textStudentSupportChatMessage.setText("")
        }

        binding.buttonStudentSupportChatImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE)
        }
    }

    override fun onResume() {
        super.onResume()

        (binding.recyclerStudentSupportChat.adapter as? StudentSupportChatAdapter)?.startListening()
    }

    override fun onPause() {
        super.onPause()

        (binding.recyclerStudentSupportChat.adapter as? StudentSupportChatAdapter)?.stopListening()
    }

    private fun putImageInStorage(storageReference: StorageReference, uri: Uri, key: String?) {
        storageReference.putFile(uri)
            .addOnSuccessListener(
                this
            ) { taskSnapshot -> // After the image loads, get a public downloadUrl for the image
                // and add it to the message.
                val auth = FirebaseAuth.getInstance()
                val user = auth.currentUser

                taskSnapshot.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        db.reference
                            .child(MESSAGES_CHILD)
                            .child(studentUid ?: user?.uid ?: "")
                            .child(key!!)
                            .child("imageUrl")
                            .setValue(uri.toString())
                    }
            }
            .addOnFailureListener(this) { e ->
                Timber.w(e)
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK && data != null) {
                val uri = data.data
                val auth = FirebaseAuth.getInstance()
                val user = auth.currentUser

                val tempMessage =
                    ChatMessage(null, user?.displayName ?: ANONYMOUS, user?.photoUrl.toString(), LOADING_IMAGE_URL)

                db.reference.child(MESSAGES_CHILD)
                    .child(studentUid ?: user?.uid ?: "")
                    .push()
                    .setValue(
                        tempMessage,
                        DatabaseReference.CompletionListener { databaseError, databaseReference ->
                            if (databaseError != null) {
                                Timber.w(
                                    databaseError.toException()
                                )
                                return@CompletionListener
                            }

                            // Build a StorageReference and then upload the file
                            val key = databaseReference.key
                            val storageReference = FirebaseStorage.getInstance()
                                .getReference(user!!.uid)
                                .child(key!!)
                                .child(uri!!.lastPathSegment!!)
                            putImageInStorage(storageReference, uri, key)
                        })

                if (studentUid != null) {
                    db.reference.child(CHATS_CHILD).child(studentUid ?: "").child("lastMessage").setValue("IMAGE")
                } else {
                    val chat = Chat(user?.displayName ?: "", user?.photoUrl.toString(), "IMAGE", user?.uid ?: "")
                    db.reference.child(CHATS_CHILD).child(user?.uid ?: "").setValue(chat)
                }
            }
        }
    }

    companion object {
        private const val MESSAGES_CHILD = "messages"
        private const val CHATS_CHILD = "chats"
        private const val ANONYMOUS = "anonymous"
        private const val REQUEST_IMAGE = 2
        private const val LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif"

        const val EXTRA_STUDENT_UID = "EXTRA_STUDENT_UID"
    }
}
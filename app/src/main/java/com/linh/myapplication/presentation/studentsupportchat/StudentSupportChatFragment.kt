package com.linh.myapplication.presentation.studentsupportchat

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Logger
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.linh.myapplication.databinding.FragmentStudentSupportChatBinding
import com.linh.myapplication.domain.ChatMessage
import timber.log.Timber

class StudentSupportChatFragment : Fragment() {
    private lateinit var binding: FragmentStudentSupportChatBinding

    private lateinit var db: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentSupportChatBinding.inflate(inflater, container, false)

        FirebaseDatabase.getInstance().setLogLevel(Logger.Level.DEBUG)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseDatabase.getInstance()
        val messagesRef = db.reference.child(MESSAGES_CHILD)

        val options = FirebaseRecyclerOptions.Builder<ChatMessage>()
            .setQuery(messagesRef, ChatMessage::class.java)
            .build()

        Timber.d(options.snapshots.toString())

        val layoutManager = LinearLayoutManager(requireContext())
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

        binding.buttonStudentSupportChatSend.setOnClickListener {
            val message = ChatMessage(binding.textStudentSupportChatMessage.text.toString(), currentUser ?: ANONYMOUS)
            db.reference.child(MESSAGES_CHILD).push().setValue(message)
            binding.textStudentSupportChatMessage.setText("")
        }

        binding.buttonStudentSupportChatImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE)
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
                    ChatMessage(null, user?.displayName ?: ANONYMOUS, user?.photoUrl.toString() ?: "", LOADING_IMAGE_URL)
                db.reference.child(MESSAGES_CHILD).push()
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
            }
        }
    }

    private fun putImageInStorage(storageReference: StorageReference, uri: Uri, key: String?) {
        storageReference.putFile(uri)
            .addOnSuccessListener(
                requireActivity()
            ) { taskSnapshot -> // After the image loads, get a public downloadUrl for the image
                // and add it to the message.
                val auth = FirebaseAuth.getInstance()
                val user = auth.currentUser

                taskSnapshot.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        val friendlyMessage =
                            ChatMessage(null, user?.displayName ?: ANONYMOUS, user?.photoUrl.toString() ?: "", uri.toString())
                        db.reference
                            .child(MESSAGES_CHILD)
                            .child(key!!)
                            .setValue(friendlyMessage)
                    }
            }
            .addOnFailureListener(requireActivity()) { e ->
                Timber.w(e)
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

    companion object {
        const val MESSAGES_CHILD = "messages"
        const val ANONYMOUS = "anonymous"
        private const val REQUEST_IMAGE = 2
        private const val LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif"
    }
}
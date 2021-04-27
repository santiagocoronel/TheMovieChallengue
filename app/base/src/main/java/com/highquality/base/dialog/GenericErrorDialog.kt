package com.highquality.base.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.highquality.base.presenter.BaseActivity
import com.example.themoviechallenge.base.R
import com.example.themoviechallenge.base.databinding.DialogGenericErrorBinding


class GenericErrorDialog : DialogFragment() {

    private var binding: DialogGenericErrorBinding? = null
    private var requestCode = 0
    private var listener: OnClickListener? = null
    private var messageDialog: String? = null
    private var titleDialog: String? = null
    private var iconDrawable: Int? = null

    companion object {

        const val MESSAGE_DIALOG = "message"
        const val TITLE_DIALOG = "title"
        const val IMAGE_DIALOG = "logo"

        @JvmStatic
        fun newInstance(
            activity: BaseActivity<*>, layoutId: Int, drawableId: Int, title: String,
            message: String, txtButton: String, listener: OnClickListener,
            requestCode: Int
        ): GenericErrorDialog {

            val errorDialog = GenericErrorDialog()
            val arguments = Bundle()
            arguments.putString(MESSAGE_DIALOG, message)
            arguments.putString(TITLE_DIALOG, title)
            arguments.putInt(IMAGE_DIALOG, drawableId)
            errorDialog.arguments = arguments
            errorDialog.listener = listener

            return errorDialog
        }

    }

    override fun onStart() {
        super.onStart()
        initializeDialog()
        loadDataDialog()
        setListeners()
    }

    private fun initializeDialog() {

        if (dialog != null) {
            dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        setCancelable(false)
    }

    private fun loadDataDialog() {

        binding?.let {

            binding?.textviewTitle?.text = titleDialog
            binding?.textviewSubtitle?.text = messageDialog

            iconDrawable?.let { it1 ->
                Glide.with(requireContext())
                    .asBitmap()
                    .dontAnimate()
                    .fitCenter()
                    .load(iconDrawable)
                    .placeholder(it1)
                    .into(it.imageView)
            }

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.dialog_generic_error, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogGenericErrorBinding.bind(view)
        if (arguments != null) {
            titleDialog = arguments?.getString(TITLE_DIALOG)
            messageDialog = arguments?.getString(MESSAGE_DIALOG)
            iconDrawable = arguments?.getInt(IMAGE_DIALOG)
        }

    }

    fun setListeners() {
        if (listener != null) {
            binding?.buttonOk?.setOnClickListener { dismissDialog() }
        }
    }

    private fun dismissDialog() {
        dismiss()
    }

    interface OnClickListener {
        fun btnClick(requestCode: Int) {

        }
    }

}
package com.highquality.base.presenter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.themoviechallenge.base.R
import com.highquality.base.dialog.GenericErrorDialog
import java.lang.reflect.ParameterizedType
import java.util.*


abstract class BaseFragment<T : ViewBinding> : Fragment(),
    GenericErrorDialog.OnClickListener {

    lateinit var binding: T
    private var activity: BaseActivity<*>? = null

    companion object {
        const val GENERIC_ERROR = 100
        const val NO_CONNECTION_ERROR = 101
    }

    protected var TAG_SCREEN: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observerCommons()
        setupObserversViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz = type.actualTypeArguments[0] as Class<*>
        val method = clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        binding = method.invoke(null, layoutInflater, container, false) as T
        TAG_SCREEN = "[" + javaClass.simpleName + "]"
        Log.i("SCREEN", "*********************")
        Log.i("SCREEN", TAG_SCREEN!!)
        Log.i("SCREEN", "*********************")

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as BaseActivity<*>
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

    abstract fun getViewModel(): BaseViewModel?

    abstract fun setupObserversViewModel()

    abstract fun init()

    protected open fun hideKeyboard() {
        val view: View? = requireActivity().getCurrentFocus()
        if (view != null) {
            val imm: InputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    protected open fun observerCommons() {
        getViewModel()?.mutableThrowables?.observe(this) { mThrow -> throwError(mThrow) }
        getViewModel()?.mutableConnection?.observe(
            this
        ) { isConexion -> if (!isConexion) showNoConnectionError() }
    }

    open fun throwError(throwable: Throwable?) {
        when (throwable) {
            else -> showGenericError()
        }
    }

    protected open fun showNoConnectionError(title: String? = null, message: String? = null) {
        GenericErrorDialog.newInstance(
            activity = requireActivity() as BaseActivity<*>,
            layoutId = R.layout.dialog_generic_error,
            drawableId = R.drawable.ic_generic_error,
            title = "Oopss!",
            message = message ?: "Al parecer no tienes conexion a internet",
            txtButton = "Entendido",
            listener = this,
            requestCode = NO_CONNECTION_ERROR
        ).show(activity?.supportFragmentManager!!, requireActivity().localClassName)
    }

    protected open fun showGenericError(title: String? = null, message: String? = null) {
        GenericErrorDialog.newInstance(
            activity = requireActivity() as BaseActivity<*>,
            layoutId = R.layout.dialog_generic_error,
            drawableId = R.drawable.ic_generic_error,
            title = "Lo sentimos",
            message = message ?: "Algo salio mal, intentelo nuevamente mas tarde",
            txtButton = "Entendido",
            listener = this,
            requestCode = GENERIC_ERROR
        ).show(activity?.supportFragmentManager!!, requireActivity().localClassName)
    }


    protected open fun getBaseActivity(): BaseActivity<*>? {
        return activity
    }

    open fun disableBackOnScreen() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {}
        }
        Objects.requireNonNull(getBaseActivity())?.getOnBackPressedDispatcher()?.addCallback(
            viewLifecycleOwner,
            callback
        )
    }

}

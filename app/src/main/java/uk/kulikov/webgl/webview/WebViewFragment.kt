package uk.kulikov.webgl.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.webkit.WebViewAssetLoader
import uk.kulikov.webgl.databinding.FragmentMainBinding
import uk.kulikov.webgl.databinding.FragmentWebviewBinding

class WebViewFragment : Fragment() {
    private var _binding: FragmentWebviewBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val assetLoader = WebViewAssetLoader.Builder()
            .addPathHandler("/assets/", WebViewAssetLoader.AssetsPathHandler(view.context))
            .build()

        binding.webview.settings.javaScriptEnabled = true
        binding.webview.webViewClient = LocalContentWebViewClient(assetLoader)
        binding.webview.loadUrl("https://appassets.androidplatform.net/assets/index.html")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
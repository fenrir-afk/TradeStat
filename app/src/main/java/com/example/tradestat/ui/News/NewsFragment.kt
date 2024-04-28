package com.example.tradestat.ui.News

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.tradestat.R
import com.example.tradestat.databinding.FragmentNewsBinding
import com.google.android.material.snackbar.Snackbar


class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // val newsViewModel =
        // ViewModelProvider(this)[NewsViewModel::class.java]

        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        // Restore the text from the savedInstanceState if it exists
        if (savedInstanceState != null) {
            binding.editTextSearch.setText(savedInstanceState.getString("searchText"))
        }
        //get data from adapte and activate listener above
        binding.editTextSearch.setOnEditorActionListener(TextView.OnEditorActionListener { textView, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                binding.editTextSearch.clearFocus()
                binding.cardsLayout.removeAllViews()
                if (isNetworkAvailable(requireContext())) {
                    newsViewModel.getNews("https://rb.ru/search/?query=" + binding.editTextSearch.text.toString())
                    //hide keyboard
                    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow( binding.editTextSearch.windowToken, 0)
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    showSnackBar()
                    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow( binding.editTextSearch.windowToken, 0)
                }
                true
            } else {
                false
            }
        })
        newsViewModel.articlesLiveData.observe(viewLifecycleOwner) {
            if (it.isEmpty()){
                if (binding.progressBar.visibility == View.VISIBLE){
                    Toast.makeText(context,"Nothing wos found",Toast.LENGTH_LONG).show()
                    binding.progressBar.visibility = View.GONE
                }
            }else{
                addNewsCards(it,newsViewModel.imgUrls,newsViewModel.dateArr)
            }
        }

        return binding.root
    }

    private fun showSnackBar(){
        val snackBar = Snackbar.make(requireView(), "Something went wrong,internet is gone", Snackbar.LENGTH_LONG)
        binding.progressBar.visibility = View.INVISIBLE
        snackBar.setAction("Try again...") {
            if (isNetworkAvailable(requireContext())){
                newsViewModel.getNews("https://rb.ru/search/?query=" + binding.editTextSearch.text.toString())
                binding.progressBar.visibility = View.VISIBLE
            }else{
                showSnackBar()
            }
        }
        snackBar.show()
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }


    private fun addNewsCards(
        article: List<String>,
        imgUrls: MutableList<String>,
        dateArr: MutableList<String>

    ) {
        for (i in imgUrls.indices){
            val cardView = CardView(requireContext())
            val cardParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, // CardView width
                LinearLayout.LayoutParams.WRAP_CONTENT // CardView height
            )
            cardParams.setMargins(60, 60, 60, 0)
            cardView.layoutParams = cardParams
            cardView.radius = 30F
            cardView.setCardBackgroundColor(Color.WHITE)


            //cardView.setContentPadding(10, 10, 10, 10)
            val image = ImageView(context)
            Glide.with(requireContext())
                .load(imgUrls[i])
                .into(image)
            val imageParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // CardView width
                LinearLayout.LayoutParams.WRAP_CONTENT // CardView height
            )
            imageParams.setMargins(0, 0, 0, 15)
            image.layoutParams = imageParams


            val text = TextView(context)
            text.setTextColor(Color.BLACK)
            val textParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, // CardView width
                LinearLayout.LayoutParams.WRAP_CONTENT // CardView height
            )
            text.textSize = 16f
            text.text = article[i]
            textParams.setMargins(15, 0, 15, 15)
            text.layoutParams = textParams


            val linearLayout = LinearLayout(context)
            linearLayout.orientation = LinearLayout.VERTICAL
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // CardView width
                LinearLayout.LayoutParams.WRAP_CONTENT // CardView height
            )
            linearLayout.layoutParams = layoutParams
            linearLayout.addView(image)
            linearLayout.addView(text)
            cardView.addView(linearLayout)
            val dateText = TextView(context)
            dateText.setTextColor(Color.WHITE)
            val dateTextParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, // CardView width
                LinearLayout.LayoutParams.WRAP_CONTENT // CardView height
            ).apply {
                setMargins(resources.getDimension(R.dimen.base_start_maring).toInt(), resources.getDimension(R.dimen.big_start_maring).toInt(), resources.getDimension(R.dimen.base_start_maring).toInt(), 0)
                gravity = Gravity.CENTER
            }
            dateText.text = "Date:${dateArr[i]}"
            dateText.layoutParams = dateTextParams
            dateText.textSize = 16f
            binding.cardsLayout.addView(dateText)
            binding.cardsLayout.addView(cardView)
        }
        val view = View(context)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, // CardView width
            100 // CardView height
        )
        view.layoutParams = layoutParams
        binding.cardsLayout.addView(view)
        binding.progressBar.visibility = View.GONE

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Save the text from the EditText to the savedInstanceState
        outState.putString("searchText", binding.editTextSearch.text.toString())
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
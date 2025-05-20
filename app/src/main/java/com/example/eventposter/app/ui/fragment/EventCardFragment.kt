package com.example.eventposter.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.eventposter.R
import com.example.eventposter.app.ui.viewmodel.EventCardViewModel
import com.example.eventposter.app.ui.adapters.recycler.FeedbackAdapter
import com.example.eventposter.databinding.FragmentEventCardBinding
import kotlinx.coroutines.launch

class EventCardFragment : Fragment() {

    private var _binding: FragmentEventCardBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val EVENT_CARD = "EVENT_CARD"
        private const val FB_DIALOG_TAG = "FB_DIALOG_TAG"
        fun newInstance() = EventCardFragment()
    }

    private lateinit var vm: EventCardViewModel
    private var eventId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vm = ViewModelProvider(this)[EventCardViewModel::class.java]

        _binding = FragmentEventCardBinding.inflate(inflater, container, false)

        eventId = requireArguments().getInt(EVENT_CARD)
        eventId?.let { vm.setEventId(it) }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.event.collect { event ->
                    binding.tvEventName.text = event?.name
                    binding.tvEventLocation.text = event?.address

                    Glide.with(requireContext())
                        .load(event?.posterUrl)
                        .error(R.drawable.ic_image_not_supported_24dp)
                        .centerCrop()
                        .into(binding.ivEventCardPoster)
                }
            }
        }

        binding.ivBackEventCard.setOnClickListener {
            removeFragment()
        }

        val fbAdapter = FeedbackAdapter(listOf())
        binding.rvPreviewFeedbacks.adapter = fbAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.feedbacks.collect { feedbacks ->
                    fbAdapter.setFeedbacks(feedbacks)
                    fbAdapter.notifyDataSetChanged()
                }
            }
        }

        binding.btnCreateFeedback.setOnClickListener {
            openFeedbackForm()
        }

//        val adapter = ImageSliderAdapter(
//            context,
//            imageList =
//        )
//        binding.vpEventImages.adapter = adapter
    }

    private fun removeFragment() {
        requireActivity()
            .findNavController(R.id.nav_host_fragment_activity_main)
            .popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openFeedbackForm() {
        val fragment = eventId?.let { FormFeedbackFragment.newInstance(it) }
        fragment?.show(parentFragmentManager, FB_DIALOG_TAG)
    }

}
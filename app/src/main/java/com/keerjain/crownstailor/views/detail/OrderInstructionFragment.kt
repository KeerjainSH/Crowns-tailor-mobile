package com.keerjain.crownstailor.views.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.data.entities.product.ProductListItem
import com.keerjain.crownstailor.databinding.FragmentOrderInstructionBinding
import com.keerjain.crownstailor.views.MainActivity

class OrderInstructionFragment : Fragment() {

    private var _binding: FragmentOrderInstructionBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentActivity: MainActivity
    private val args: OrderInstructionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderInstructionBinding.inflate(inflater, container, false)

        currentActivity = activity as MainActivity
        currentActivity.removeBottomBar()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentActivity.setSupportActionBar(binding.topAppBar)
        currentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        bindInformation(args.product)
    }

    private fun bindInformation(product: ProductListItem) {
        binding.tvProductNameInstruction.text = product.productDetail.productName

        binding.tvArm.text = resources.getString(
            R.string.length_format,
            String.format("%.0f", product.orderDetail.armSize)
        )
        binding.tvNeck.text = resources.getString(
            R.string.length_format,
            String.format("%.0f", product.orderDetail.neckSize)
        )
        binding.tvWaist.text = resources.getString(
            R.string.length_format,
            String.format("%.0f", product.orderDetail.waistSize)
        )
        binding.tvHeight.text = resources.getString(
            R.string.length_format,
            String.format("%.0f", product.orderDetail.bodyHeight)
        )
        binding.tvChest.text = resources.getString(
            R.string.length_format,
            String.format("%.0f", product.orderDetail.chestSize)
        )
        binding.tvWeight.text = resources.getString(
            R.string.weight_format,
            String.format("%.0f", product.orderDetail.bodyWeight)
        )

//        if (product.orderDetail.design != null && product.orderDetail.design != "") {
//            binding.tvDesignLinkInstruction.text = resources.getString(R.string.design_link)
//            binding.tvDesignLinkInstruction.isClickable = true
//            binding.tvDesignLinkInstruction.isFocusable = true
//            binding.tvDesignLinkInstruction.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_blue_color))
//            binding.tvDesignLinkInstruction.setOnClickListener {
//                val toBrowser = Intent(Intent.ACTION_VIEW, Uri.parse(product.orderDetail.design))
//                startActivity(toBrowser)
//            }
//        } else {
//            binding.tvDesignLinkInstruction.text = resources.getString(R.string.no_specific_design)
//        }

        binding.tvProductInstruction.text = product.orderDetail.instructions
    }
}
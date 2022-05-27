package com.sema.stateflow.ui.plan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sema.stateflow.databinding.PlanFragmentBinding
import com.sema.stateflow.ui.BaseFragment
import com.sema.stateflow.util.showView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PlanFragment : BaseFragment<PlanFragmentBinding>() {

    private val planViewModel by viewModels<PlanViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initializeObservers()
    }

    private fun PlanFragmentBinding.initializeObservers() {
        lifecycleScope.launchWhenStarted {
            planViewModel.investorProductResult.collect { investorProductResult ->
                investorProductResult.loading.let { loading.showView(it) }
                investorProductResult.error?.let { showToast(it) }
                investorProductResult.success?.let { totalPlanValue = it.totalPlanValue }
            }
        }
    }

}
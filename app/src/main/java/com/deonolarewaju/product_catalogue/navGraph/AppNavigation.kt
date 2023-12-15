package com.deonolarewaju.product_catalogue.navGraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deonolarewaju.product_catalogue.domain.model.Product
import com.deonolarewaju.product_catalogue.navGraph.Route.HomeScreenRoute
import com.deonolarewaju.product_catalogue.navGraph.Route.ProductDetailsScreenRoute
import com.deonolarewaju.product_catalogue.presentation.details.ProductDetailsScreen
import com.deonolarewaju.product_catalogue.presentation.details.ProductDetailsViewModel
import com.deonolarewaju.product_catalogue.presentation.home.HomeViewModel
import com.deonolarewaju.product_catalogue.presentation.home.ProductListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = HomeScreenRoute) {
        composable(HomeScreenRoute) {
            val viewModel: HomeViewModel = hiltViewModel()
            val state = viewModel.state
            ProductListScreen(
                state = state,
                event = viewModel::onEvent,
                navigateToDetails = {
                    navigateToDetails(
                        navController = navController,
                        product = it
                    )
                }
            )
        }
        composable(ProductDetailsScreenRoute) {
            navController.previousBackStackEntry?.savedStateHandle?.get<Product?>("product")
                ?.let { product ->
                    ProductDetailsScreen(
                        product.id,
                        navigateUp = { navController.navigateUp() },
                    )
                }
        }
    }
}

private fun navigateToDetails(navController: NavController, product: Product) {
    navController.currentBackStackEntry?.savedStateHandle?.set("product", product)
    navController.navigate(
        route = ProductDetailsScreenRoute
    )
}
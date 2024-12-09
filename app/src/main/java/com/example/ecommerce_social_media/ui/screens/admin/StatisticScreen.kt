package com.example.ecommerce_social_media.ui.screens.admin

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommerce_social_media.R
import com.example.ecommerce_social_media.ui.auth.Viewmodel.StatisticViewModel
import com.example.ecommerce_social_media.ui.components.TopBarHeaderText
import com.example.ecommerce_social_media.ui.navigation.ESocialMediaAppRoute
import com.example.ecommerce_social_media.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticScreen(statisticViewModel: StatisticViewModel = hiltViewModel()) {
    var month by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val statistics by statisticViewModel.postStatistics.collectAsState()

    val errorColor = Color(android.graphics.Color.parseColor("#Ea6d35"))

    LaunchedEffect(statisticViewModel.postStatistics) {
        Log.d("Statistics", "Received statistics: $statistics")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopBarHeaderText(value = "Thống kê") },
                navigationIcon = {
                    IconButton(onClick = { ESocialMediaAppRoute.navigateTo(Screen.AdminHomeScreen) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_previous),
                            contentDescription = "Quay về trang chủ"
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(
                        android.graphics.Color.parseColor(
                            "#Ea6d35"
                        )
                    )
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Chọn tháng và năm",
                style = MaterialTheme.typography.headlineMedium,
                color = errorColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input field for month
            TextField(
                value = month,
                onValueChange = { month = it },
                label = { Text("Tháng") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Input field for year
            TextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("Năm") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val monthValue = month.toIntOrNull()
                    val yearValue = year.toIntOrNull()

                    // Validation logic
                    errorMessage = when {
                        monthValue == null || yearValue == null -> "Vui lòng nhập số hợp lệ."
                        monthValue !in 1..12 -> "Tháng phải từ 1 đến 12."
                        yearValue !in 2000..2024 -> "Năm phải từ 2000 đến 2024."
                        else -> {
                            statisticViewModel.fetchPostStatisticsByMonthYear(monthValue, yearValue)
                            ""
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = errorColor)
            ) {
                Text(text = "Lấy thống kê")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }


            if (statistics.isNotEmpty()) {
                statistics.forEach { stat ->
                    Text(text = "Tháng: ${stat.month}, Năm: ${stat.year}, Số lượng bài đăng: ${stat.productpostCount}", color = errorColor)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            } else {
                Text(text = "Không có thống kê nào.", color = MaterialTheme.colorScheme.error)
            }

        }
    }
}

package com.example.ecommerce_social_media.ui.screens.admin


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommerce_social_media.R
import com.example.ecommerce_social_media.data.entity.AccountResponse3
import com.example.ecommerce_social_media.data.entity.RoleResponse2
import com.example.ecommerce_social_media.data.entity.UserResponse3
import com.example.ecommerce_social_media.ui.auth.Viewmodel.AccountViewModel
import com.example.ecommerce_social_media.ui.components.TopBarHeaderText
import com.example.ecommerce_social_media.ui.navigation.ESocialMediaAppRoute
import com.example.ecommerce_social_media.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserManagementScreen(
    accountViewModel: AccountViewModel = hiltViewModel()
) {
    val accountList by accountViewModel.accountList.collectAsState(initial = emptyList())
    var selectedAccount by remember { mutableStateOf<AccountResponse3?>(null) }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val errorColor = Color(android.graphics.Color.parseColor("#Ea6d35"))

    LaunchedEffect(Unit) {
        accountViewModel.getAllAccounts()  // Gọi API để lấy danh sách tài khoản
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopBarHeaderText(value = "Quản lý tài khoản") },
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
                text = "Thông tin tài khoản",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // TextField để nhập username
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // TextField để nhập email
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Nút thêm/cập nhật tài khoản
            Button(
                onClick = {
                    val newAccount = AccountResponse3(
                        id = selectedAccount?.id ?: 0,
                        user = UserResponse3(
                            id = selectedAccount?.user?.id ?: 0,
                            username = username,
                            first_name = selectedAccount?.user?.first_name ?: "",
                            last_name = selectedAccount?.user?.last_name ?: "",
                            email = email
                        ),
                        avatar = selectedAccount?.avatar,
                        phone_number = selectedAccount?.phone_number,
                        account_status = selectedAccount?.account_status ?: false,
                        gender = selectedAccount?.gender ?: true,
                        role = selectedAccount?.role ?: RoleResponse2(2, "User", true)
                    )
                    if (selectedAccount == null) {
                        accountViewModel.addAccount(newAccount)
                    } else {
                        accountViewModel.updateAccount(newAccount)
                    }
                    selectedAccount = null
                    username = ""
                    email = ""
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = errorColor)
            ) {
                Text(text = if (selectedAccount == null) "Thêm tài khoản" else "Cập nhật tài khoản")
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(accountList) { account ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(account.user.username, modifier = Modifier.weight(1f))

                        // Nút chỉnh sửa tài khoản
                        Button(onClick = {
                            selectedAccount = account
                            username = account.user.username
                            email = account.user.email
                        },
                            colors = ButtonDefaults.buttonColors(containerColor = errorColor)

                        ) {
                            Text("Sửa")
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(onClick = {
                            accountViewModel.deleteAccount(account.id)
                        },
                            colors = ButtonDefaults.buttonColors(containerColor = errorColor)
                        ) {
                            Text("Xóa")
                        }
                    }
                }
            }
        }
    }
}

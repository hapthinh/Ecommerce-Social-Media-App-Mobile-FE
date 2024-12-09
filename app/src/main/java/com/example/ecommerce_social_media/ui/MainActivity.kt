            package com.example.ecommerce_social_media.ui

            import android.os.Bundle
            import androidx.activity.ComponentActivity
            import androidx.activity.compose.setContent
            import androidx.appcompat.app.AppCompatActivity
            import androidx.compose.runtime.Composable
            import com.example.ecommerce_social_media.ui.navigation.AppNavigationGraph
            import com.example.ecommerce_social_media.ui.theme.EcommercesocialmediaTheme
            import dagger.hilt.android.AndroidEntryPoint

            @AndroidEntryPoint
            class MainActivity : AppCompatActivity() {
                override fun onCreate(savedInstanceState: Bundle?) {
                    super.onCreate(savedInstanceState)
                    setContent {
                        EcommercesocialmediaTheme {
                            AppEntryPoint()
                        }
                    }
                }
            }

            @Composable
            fun AppEntryPoint(){
                AppNavigationGraph()
            }

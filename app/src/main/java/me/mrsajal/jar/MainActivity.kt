package me.mrsajal.jar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.mrsajal.jar.ui.theme.JarTheme
import me.mrsajal.jar.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JarTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GoldLockerScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoldLockerScreen() {
    Scaffold(
        topBar = { TopBar() }, // Adds the top bar with tabs at the top of the screen
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF1D1B41)) // Background color for entire screen
                    .padding(paddingValues) // Ensures content does not overlap with the top bar
            ) {
                EnhancedBalanceSection()
                Spacer(modifier = Modifier.height(16.dp))
                TransactionHistory() // Add other components as needed
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    Column {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF1D1B41),
                titleContentColor = Color.White
            ),
            title = {},
            actions = {
                IconButton(onClick = { /* Handle profile icon click */ }) {
                    Icon(Icons.Default.AccountCircle, contentDescription = "Profile")
                }
                IconButton(onClick = { /* Handle support icon click */ }) {
                    Icon(Icons.Default.Close, contentDescription = "Support")
                }
                IconButton(onClick = { /* Handle notifications icon click */ }) {
                    Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                }
            },
            modifier = Modifier.background(Color(0xFF1D1B41))
        )

        // TabRow for navigation
        TabRow(selectedTabIndex = 0, containerColor = Color(0xFF1D1B41)) {
            listOf("Gold", "Jar UPI", "Nek", "Loan").forEachIndexed { index, title ->
                Tab(
                    selected = index == 0,
                    onClick = { /* Handle tab click */ },
                    text = { Text(title, color = Color.White) }
                )
            }
        }
    }
}

@Composable
fun EnhancedBalanceSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF2D1653), Color(0xFF1D1B41))
                )
            )
            .padding(16.dp)
    ) {
        // Net background image covering the balance section
        Image(
            painter = painterResource(id = R.drawable.net_bg), // Replace with your net background image
            contentDescription = "Net Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Adjust height as needed
                .align(Alignment.TopCenter)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Balance details with locker image
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("24K Gold in Locker", color = Color(0xFFDAA520), fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("0.828gm", fontSize = 28.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("₹ 1200", color = Color(0xFFDAA520), fontSize = 16.sp)
                    }
                }

                // Locker icon on the right
                Image(
                    painter = painterResource(id = R.drawable.chest), // Replace with your locker icon image
                    contentDescription = "Locker Image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(64.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Save Button positioned at the bottom of the balance section
            SaveButton()
        }
    }
}

@Composable
fun SaveButton() {
    Button(
        onClick = { /* Handle save manually */ },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text("Save Manually", color = Color.White)
    }
}


@Composable
fun TransactionHistory() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp) // Add padding to align with other components
    ) {
        Text("Today", color = Color.White, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        TransactionItem("Manual Buy", "₹20", "0.0045 gms", "7:00 PM • Sep 18")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Yesterday", color = Color.White, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        TransactionItem("Manual Buy", "₹649", "0.0045 gms", "7:00 PM • Sep 17")
    }
}

@Composable
fun TransactionItem(title: String, amount: String, grams: String, date: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2C296A), RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.AccountCircle, contentDescription = null, tint = Color.White, modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(title, color = Color.White, fontSize = 16.sp)
                Text(date, color = Color.Gray, fontSize = 12.sp)
            }
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(amount, color = Color.White, fontSize = 16.sp)
            Text(grams, color = Color.Gray, fontSize = 12.sp)
        }
    }
}

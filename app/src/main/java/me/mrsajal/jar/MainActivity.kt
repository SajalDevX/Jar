package me.mrsajal.jar

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.mrsajal.jar.ui.theme.JarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JarTheme {
                TopAppBarWithScaffold()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithScaffold() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val topAppBarState = scrollBehavior.state
    var showBalanceSection by remember { mutableStateOf(true) }

    LaunchedEffect(topAppBarState.collapsedFraction) {
        showBalanceSection = topAppBarState.collapsedFraction < 0.5f
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column {
                LargeTopAppBar(
                    title = {
                        if (topAppBarState.collapsedFraction < 0.3f) {
                            TabRow(selectedTabIndex = 0, containerColor = Color(0xFF1D1B41)) {
                                listOf(
                                    "Gold",
                                    "Jar UPI",
                                    "Nek",
                                    "Loan"
                                ).forEachIndexed { index, title ->
                                    Tab(
                                        selected = index == 0,
                                        onClick = { /* Handle tab click */ },
                                        text = { Text(title, color = Color.White) }
                                    )
                                }
                            }
                        }
                    },
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = Color(0xFF1D1B41),
                        titleContentColor = Color.White
                    ),
                    navigationIcon = {
                        if (topAppBarState.collapsedFraction < 0.3f) {
                            IconButton(onClick = { /* Handle profile icon click */ }) {
                                Icon(
                                    Icons.Default.AccountCircle,
                                    contentDescription = "Profile",
                                    tint = Color.White
                                )
                            }
                        }
                    },
                    actions = {
                        if (topAppBarState.collapsedFraction < 0.3f) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(end = 16.dp)
                            ) {
                                // First icon with background
                                Box(
                                    modifier = Modifier
                                        .background(color = Color(0xff241F33), shape = CircleShape)
                                        .size(40.dp) // Set the size of the circular background
                                ) {
                                    IconButton(onClick = { /* Handle rewards click */ }) {
                                        Image(
                                            painter = painterResource(R.drawable.headset),
                                            contentDescription = "Rewards",
                                            modifier = Modifier.size(24.dp) // Adjust icon size
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(12.dp)) // Space between icons

                                Box(
                                    modifier = Modifier
                                        .width(78.dp) // Set custom width
                                        .height(40.dp)
                                        .background(color = Color(0xff241F33), shape = RoundedCornerShape(20.dp)) // Rounded corners
                                        .clip(RoundedCornerShape(20.dp)) // Clip to rounded corners
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        IconButton(
                                            onClick = { /* Handle support click */ },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Image(
                                                painter = painterResource(R.drawable.rewards),
                                                contentDescription = "Support",
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }

                                        IconButton(
                                            onClick = { /* Handle notifications click */ },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                Icons.Default.Notifications,
                                                contentDescription = "Notifications",
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    }
                                }

                            }
                        }
                    }
                )

                // Show the TabRow in the app bar when collapsed
                if (topAppBarState.collapsedFraction >= 0.3f) {
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
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1D1B41))
                .padding(padding)
        ) {
            if (showBalanceSection) {
                item {
                    EnhancedBalanceSection(visibility = 1f - topAppBarState.collapsedFraction)
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Today", color = Color.White, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(30) { index ->
                val title = "Transaction #$index"
                val amount = "₹${(20..1000).random()}"
                val grams = "${(0.004..0.5)} gms"
                val date = "7:00 PM • Sep ${(1..30).random()}"
                TransactionItem(title, amount, grams, date)
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Yesterday", color = Color.White, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(10) { index ->
                val title = "Transaction #${index + 30}"
                val amount = "₹${(20..1000).random()}"
                val grams = "${(0.004..0.5)} gms"
                val date = "6:00 PM • Sep ${(1..30).random()}"
                TransactionItem(title, amount, grams, date)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun EnhancedBalanceSection(visibility: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF2D1653), Color(0xFF1D1B41))
                )
            )
            .padding(16.dp)
            .alpha(visibility)
    ) {
        Image(
            painter = painterResource(id = R.drawable.net_bg),
            contentDescription = "Net Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(Alignment.TopCenter)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("24K Gold in Locker", color = Color(0xFFDAA520), fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "0.828gm",
                            fontSize = 28.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("₹ 1200", color = Color(0xFFDAA520), fontSize = 16.sp)
                    }
                }

                Image(
                    painter = painterResource(id = R.drawable.chest),
                    contentDescription = "Locker Image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(64.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

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
            Icon(
                Icons.Default.Face,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
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

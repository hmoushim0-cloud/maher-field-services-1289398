package sa.factory.maherfieldservices.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sa.factory.maherfieldservices.model.MaherFieldItem
import sa.factory.maherfieldservices.ui.theme.*
import sa.factory.maherfieldservices.ui.viewmodel.MaherFieldUiState
import sa.factory.maherfieldservices.ui.viewmodel.MaherFieldViewModel

@Composable
fun HomeScreen(
    state: MaherFieldUiState,
    viewModel: MaherFieldViewModel,
    onItemClick: (MaherFieldItem) -> Unit
) {
    val categories = listOf("الكل", "تصميم القطع", "تركيب Snap Fit", "اختبار المتانة", "خدمات NFC")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header
        Column {
            Text("خدمات الترابط الذكي — حلول Snap Fit متكاملة مع NFC و GPS", fontSize = 22.sp, fontWeight = FontWeight.Black, color = BrandTextPrimary)
            Text("ربط ذكي مع تقنية NFC و GPS لخدمات فورية ودقيقة", fontSize = 12.sp, color = BrandTextSecondary)
        }

        // Hardware Status Capsule
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BrandAccentGlow),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("العتاد الحي: ${state.telemetry?.peripheralName ?: 'Mada Contactless EMV L2 NFC + GPS Dispatch'}", fontWeight = FontWeight.Bold, color = BrandPrimary, fontSize = 13.sp)
                    Text(state.telemetry?.liveReading ?: "NFC Tag: 0x0A | GPS: 24.7136°N, 46.6753°E | Speed: 22 km/h", fontSize = 11.sp, color = BrandTextSecondary)
                }
                Badge(containerColor = BrandPrimary) {
                    Text(state.telemetry?.status ?: "ONLINE", color = Color.Black, fontWeight = FontWeight.Bold, modifier = Modifier.padding(2.dp))
                }
            }
        }

        // Category Filter Chips
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(categories) { cat ->
                val isSelected = state.selectedCategory == cat
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) BrandPrimary else BrandSurface,
                    modifier = Modifier.clickable { viewModel.selectCategory(cat) }
                ) {
                    Text(
                        text = cat,
                        color = if (isSelected) Color.Black else BrandTextSecondary,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                    )
                }
            }
        }

        // Active Items List
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxSize()) {
            items(state.filteredCatalog) { item ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = BrandSurface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemClick(item) }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(item.title, fontWeight = FontWeight.Bold, color = BrandTextPrimary, fontSize = 15.sp)
                                Surface(shape = RoundedCornerShape(6.dp), color = BrandPrimary.copy(alpha = 0.2f)) {
                                    Text(item.badge, fontSize = 9.sp, color = BrandPrimary, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(item.description, fontSize = 12.sp, color = BrandTextSecondary, maxLines = 2)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("${item.priceSar} ر.س", fontWeight = FontWeight.Black, color = BrandSecondary, fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Button(
                                onClick = { viewModel.addToCart(item) },
                                colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("إضافة", fontSize = 11.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

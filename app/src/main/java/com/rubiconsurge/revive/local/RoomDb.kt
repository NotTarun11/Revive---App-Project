package com.rubiconsurge.revive.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rubiconsurge.revive.R
import com.rubiconsurge.revive.models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [
        Advertisement::class,
        Manufacturer::class,
        Review::class,
        User::class,
        PaymentProvider::class,
        UserPaymentProvider::class,
        Product::class,
        BookmarkItem::class,
        Location::class,
        CartItem::class,
        Order::class,
        OrderItem::class,
        OrderPayment::class,
        Notification::class,
        ProductColor::class,
        ProductSize::class,
    ],
    version = 1, exportSchema = false)
abstract class RoomDb : RoomDatabase() {

    /** A function that used to retrieve Room's related dao instance */
    abstract fun getDao(): RoomDao

    class PopulateDataClass @Inject constructor(
        private val client: Provider<RoomDb>,
        private val scope: CoroutineScope,
    ) : RoomDatabase.Callback() {
        private val description =
            "This is the description text that is supposed to be long enough to show how the UI looks, so it's not a real text.\n"
        private val manufacturers = listOf(
            Manufacturer(id = 1, name = "Dell", icon = R.drawable.ic_dell),
            Manufacturer(id = 2, name = "HP", icon = R.drawable.hp),
        )
        private val advertisements = listOf(
            Advertisement(1, R.drawable.banner_1, 1, 0),
            Advertisement(2, R.drawable.banner_2, 2, 0),
            Advertisement(3, R.drawable.banner_3, 3, 0),
        )
        private val dellProducts = listOf(
            Product(
                id = 1,
                name = "Dell Xps 15 9570",
                image = R.drawable.dell_1,
                price = 149.0,
                description = "The Dell XPS 15 9570 is a high-performance laptop featuring a 15.6-inch 4K UHD display, Intel Core i7-8750H processor, NVIDIA GeForce GTX 1050 Ti graphics, 16GB RAM, and a 512GB SSD, ideal for power users and creators.",
                manufacturerId = 1,
                basicColorName = "black",
            ),
            Product(
                id = 3,
                name = "Dell Latitude 13 3320",
                image = R.drawable.dell_2,
                price = 159.0,
                description = "The Dell Latitude 13 3320 is a compact business laptop with a 13.3-inch FHD display, Intel Core i5-1135G7 processor, 8GB RAM, and a 256GB SSD, designed for productivity and portability.",
                manufacturerId = 1,
                basicColorName = "grey",
            ),
            Product(
                id = 7,
                name = "Dell Inspiron 15",
                image = R.drawable.dell_3,
                price = 120.0,
                description = "The Dell Inspiron 15 is a versatile laptop with a 15.6-inch FHD display, Intel Core i5-1135G7 processor, 8GB RAM, and a 256GB SSD, suitable for everyday computing and light multitasking.",
                manufacturerId = 1,
                basicColorName = "grey",
            ),
        )
        private val hpProducts = listOf(
            Product(
                id = 10,
                name = "HP Laptop 15-fd0032ne",
                image = R.drawable.hp_1,
                price = 149.0,
                description = "The HP Laptop 15-fd0032ne features a 15.6-inch HD display, AMD Ryzen 5 3500U processor, Radeon Vega 8 graphics, 8GB RAM, and a 512GB SSD, suitable for everyday computing and light multitasking.",
                manufacturerId = 2,
                basicColorName = "grey",
            ),

            Product(
                id = 12,
                name = "HP Pavilion X360",
                image = R.drawable.hp_2,
                price = 159.0,
                description = "The HP Pavilion x360 is a versatile 2-in-1 convertible laptop with a 14-inch Full HD touchscreen, Intel Core i5 processor, 8GB RAM, and 256GB SSD, designed for flexibility and everyday productivity.",
                manufacturerId = 2,
                basicColorName = "cream",
            ),
        )
        private val paymentProviders = listOf(
            PaymentProvider(
                id = "apple",
                title = R.string.apple_pay,
                icon = R.drawable.ic_apple,
            ),
            PaymentProvider(
                id = "master",
                title = R.string.master_card,
                icon = R.drawable.ic_master_card,
            ),
            PaymentProvider(
                id = "visa",
                title = R.string.visa,
                icon = R.drawable.ic_visa,
            ),
        )
        private val userPaymentAccounts = listOf(
            UserPaymentProvider(
                providerId = "apple",
                cardNumber = "8402-5739-2039-1234"
            ),
            UserPaymentProvider(
                providerId = "master",
                cardNumber = "3323-8202-4748-1234"
            ),
            UserPaymentProvider(
                providerId = "visa",
                cardNumber = "7483-02836-4839-1234"
            ),
        )
        private val userLocation = Location(
            address = "VIT Chennai, Kelambakkam",
            city = "Chennai",
            country = "India",
        )

        init {
            dellProducts.onEach {
                it.sizes = mutableListOf(
                    ProductSize(it.id, 38),
                    ProductSize(it.id, 40),
                    ProductSize(it.id, 42),
                    ProductSize(it.id, 44),
                )
            }
            hpProducts.onEach {
                it.sizes = mutableListOf(
                    ProductSize(it.id, 38),
                    ProductSize(it.id, 40),
                    ProductSize(it.id, 42),
                    ProductSize(it.id, 44),
                )
            }

            scope.launch {
                populateDatabase(dao = client.get().getDao(), scope = scope)
            }
        }

        private suspend fun populateDatabase(dao: RoomDao, scope: CoroutineScope) {
            /** Save users */
            scope.launch {
                dao.saveUser(
                    User(
                        userId = 1,
                        name = "Beneta Johnson",
                        profile = R.drawable.avatar_pic,
                        phone = "+91 9234515623",
                        email = "beneta@gmail.com",
                        password = "12345678",
                        token = "ds2f434ls2ks2lsj2ls",
                    )
                )
            }
            /** insert manufacturers */
            scope.launch {
                manufacturers.forEach {
                    dao.insertManufacturer(it)
                }
            }
            /** insert advertisements */
            scope.launch {
                advertisements.forEach {
                    dao.insertAdvertisement(it)
                }
            }
            /** Insert products */
            scope.launch {
                dellProducts.plus(hpProducts).forEach {
                    /** Insert the product itself */
                    dao.insertProduct(product = it)
                    /** Insert colors */
                    it.colors?.forEach { productColor ->
                        dao.insertOtherProductCopy(productColor)
                    }
                    /** Insert size */
                    it.sizes?.forEach { productSize ->
                        dao.insertSize(productSize)
                    }
                }
            }
            /** Insert payment providers */
            scope.launch {
                paymentProviders.forEach {
                    dao.savePaymentProvider(paymentProvider = it)
                }
            }
            /** Insert user's payment providers */
            scope.launch {
                userPaymentAccounts.forEach {
                    dao.saveUserPaymentProvider(it)
                }
            }
            /** Insert user's location */
            scope.launch {
                dao.saveLocation(location = userLocation)
            }
        }
    }

}
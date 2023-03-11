package com.tinydeveloper.borutoapp.domain.model

import androidx.annotation.DrawableRes
import com.tinydeveloper.borutoapp.R

sealed class OnBoardingPage(
    @DrawableRes
    val image:Int,
    val title:String,
    val description:String
){
    object First: OnBoardingPage(
        image = R.drawable.ic_heroes2,
        title = "قهرمانان",
        description = "دست اوردها و سرگذشت قهرمانان تاریخ ایران بصورت خلاصه بخوانید و با این قهرمانان اشنا شوید و برای اشنایان تعریف کنید."
    )

    object Second: OnBoardingPage(
        image = R.drawable.ic_search2,
        title = "جستجو",
        description = "در میان هزاران قهرمان تاریخ ایران نام قهرمان خود را جستجو کنید (نیاز به اینترنت دارد مگر این که اطلاعات از قبل دانلود شده باشد)"
    )

    object Third: OnBoardingPage(
        image = R.drawable.ic_saved2,
        title = "حالت افلاین",
        description = "این برنامه نیاز به اینترنت دارد تا اطلاعات را دریافت کند سپس اطلاعات در تلفن همراه شما ذخیره میشود و دفعات بعد نیاز به اینترنت ندارد"
    )
}

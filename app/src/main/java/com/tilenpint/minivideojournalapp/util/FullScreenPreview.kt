package com.tilenpint.minivideojournalapp.util

import androidx.compose.ui.tooling.preview.Preview

@Preview(
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
    widthDp = 411,
    heightDp = 891,
    device = "spec:width=411dp,height=891dp,dpi=480"
)
@Preview(
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
    widthDp = 300,
    heightDp = 600,
    device = "spec:width=411dp,height=891dp,dpi=480"
)
@Preview(
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO,
    widthDp = 411,
    heightDp = 891,
    device = "spec:width=411dp,height=891dp,dpi=480"
)
@Preview(
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO,
    widthDp = 300,
    heightDp = 600,
    device = "spec:width=411dp,height=891dp,dpi=480"
)
annotation class FullScreenPreview
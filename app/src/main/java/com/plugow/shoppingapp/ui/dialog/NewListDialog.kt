package com.plugow.shoppingapp.ui.dialog

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.core.content.ContextCompat
import com.plugow.shoppingapp.R
import org.jetbrains.anko.*
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.alert
import org.jetbrains.anko.dip

private const val ACTIVITY_PADDING = 16

class NewListDialog(ui: AnkoContext<View>) {

    var pDialog: DialogInterface

    lateinit var codeEditText: EditText
    lateinit var okButton: TextView
    lateinit var cancelButton: TextView

    init {
        with(ui) {
            pDialog = alert {
                customView {
                    verticalLayout {
                        backgroundResource = R.drawable.dialog_background
                        padding = dip(20)
                        codeEditText = editText {
                            imeOptions = EditorInfo.IME_ACTION_GO
                            hint = context.getString(R.string.new_list)
                        }.lparams(height = dip(60), width = matchParent) {
                            bottomMargin = dip(ACTIVITY_PADDING)
                        }

                        linearLayout {
                            topPadding = dip(24)
                            orientation = LinearLayout.HORIZONTAL
                            horizontalGravity = Gravity.END

                            cancelButton = textView(R.string.cancel) {
                                textSize = 20f
                                textColor = ContextCompat.getColor(ctx, R.color.colorAccent)
                            }.lparams {
                                marginEnd = dip(16)
                            }

                            okButton = textView(R.string.create) {
                                textSize = 20f
                                textColor = ContextCompat.getColor(ctx, R.color.colorAccent)
                            }
                        }
                    }
                }
            }.show()

            if (pDialog is AlertDialog) {
                (pDialog as? AlertDialog)?.window?.decorView?.backgroundColor = Color.TRANSPARENT
            }
        }
    }
}

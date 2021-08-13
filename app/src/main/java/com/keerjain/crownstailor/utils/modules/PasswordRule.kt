package com.keerjain.crownstailor.utils.modules

import com.wajahatkarim3.easyvalidation.core.rules.BaseRule

class PasswordRule : BaseRule {
    override fun getErrorMessage(): String {
        return "Password kurang dari 8 digit"
    }

    override fun validate(text: String): Boolean {
        return text.count() >= 8
    }
}
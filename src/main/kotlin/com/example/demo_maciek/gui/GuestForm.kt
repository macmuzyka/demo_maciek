package com.example.demo_maciek.gui

import com.example.demo_maciek.model.Guest
import com.example.demo_maciek.model.enums.Status
import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.VaadinDsl
import com.github.mvysny.karibudsl.v10.beanValidationBinder
import com.github.mvysny.karibudsl.v10.bind
import com.github.mvysny.karibudsl.v10.checkBox
import com.github.mvysny.karibudsl.v10.comboBox
import com.github.mvysny.karibudsl.v10.formLayout
import com.github.mvysny.karibudsl.v10.horizontalLayout
import com.github.mvysny.karibudsl.v10.init
import com.github.mvysny.karibudsl.v10.integerField
import com.github.mvysny.karibudsl.v10.isExpand
import com.github.mvysny.karibudsl.v10.textField
import com.github.mvysny.karibudsl.v10.trimmingConverter
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.checkbox.Checkbox
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField
import mu.KotlinLogging

class PersonForm : KComposite() {
    val binder = beanValidationBinder<Guest>()
    val log = KotlinLogging.logger { }
    lateinit var name: TextField
    lateinit var age: IntegerField
    lateinit var status: ComboBox<Status>
    lateinit var adults: IntegerField
    lateinit var includeKids: Checkbox
    lateinit var kids: IntegerField
    lateinit var guestsTotal: IntegerField
    var kidsCount: Int = 0
    var adultsCount: Int = 0

    val root = ui {

        formLayout {
            isExpand = true
            verticalLayout {
                horizontalLayout {
                    name = textField("Name:") {
                        bind(binder).trimmingConverter().bind(Guest::name)
                    }
                    age = integerField("Age:") {
                        bind(binder).bind(Guest::age)
                    }
                    status = comboBox("Status:") {
                        setItems(*Status.values())
                        bind(binder).bind(Guest::status)
                    }
                }
                horizontalLayout {
                    adults = integerField("Adults count:") {
                        addValueChangeListener {
                            adultsCount = this.value
                            log.info { "adults count : $adultsCount" }
                        }
                    }
                    includeKids = checkBox("Kids") {
                        addValueChangeListener {
                            kids.isEnabled = this.value == true

                        }
                    }
                    kids = integerField("Kids count") {
                        this.isEnabled = false
                        addValueChangeListener {
                            kidsCount = this.value
                            log.info { "kids count : $kidsCount" }
                        }
                    }
                    guestsTotal = integerField("Total guests") {
//                        isEnabled = false
                        addValueChangeListener {
                            log.info { "Something has changed.." }
                            if (kids.value != 0 || adults.value != 0) {
                                kidsCount = kids.value ?: 0
                                adultsCount = adults.value ?: 0
                                value = kidsCount + adultsCount
                                log.info { "value: $adultsCount" }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun (@VaadinDsl HasComponents).personForm(block: PersonForm.() -> Unit) =
    init(PersonForm(), block)

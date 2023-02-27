package com.example.demo_maciek.live_coding

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.columnFor
import com.github.mvysny.karibudsl.v10.grid
import com.github.mvysny.karibudsl.v10.isExpand
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.github.mvysny.kaributools.refresh
import com.github.mvysny.vokdataloader.buildFilter
import com.github.vokorm.dataloader.dataLoader
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.Route
import eu.vaadinonkotlin.vaadin.asFilterBar
import eu.vaadinonkotlin.vaadin.bind
import eu.vaadinonkotlin.vaadin.vokdb.asDataProvider

@Route("synonimy")
class SynonimyView:KComposite() {
    val root = ui {
        verticalLayout {
            setSizeFull()
            val grid = grid<Synonimy> {
                isExpand=true
                val filterBar = appendHeaderRow().asFilterBar(this)
                columnFor(Synonimy::id)
                columnFor(Synonimy::syn_from) {
                    filterBar
                        .forField(TextField(),this)
                        .withConverter {
                            if(it!=null) {
                                buildFilter<Synonimy> {
                                    "syn_from <% :szukam::text"("szukam" to it)
                                }
                            } else null
                        }.bind()
                }
                columnFor(Synonimy::syn_to)
                dataProvider = Synonimy.dataLoader.asDataProvider()
            }
            button("Add") {
                onLeftClick {
                    Synonimy().save()
                    grid.refresh()
                }
            }
        }
    }
}
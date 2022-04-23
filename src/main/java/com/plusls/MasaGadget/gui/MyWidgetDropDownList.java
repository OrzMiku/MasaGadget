package com.plusls.MasaGadget.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import fi.dy.masa.malilib.gui.widgets.WidgetDropDownList;
import fi.dy.masa.malilib.interfaces.IStringRetriever;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class MyWidgetDropDownList<T> extends WidgetDropDownList<T> {
    private final Consumer<T> selectedCallback;
    private final Predicate<T> shouldEnable;

    public MyWidgetDropDownList(int x, int y, int width, int height, int maxHeight, int maxVisibleEntries,
                                List<T> entries, @Nullable IStringRetriever<T> stringRetriever,
                                Consumer<T> selectedCallback, Predicate<T> shouldEnable) {
        super(x, y, width, height, maxHeight, maxVisibleEntries, entries, stringRetriever);
        this.selectedCallback = selectedCallback;
        this.shouldEnable = shouldEnable;
    }

    @Override
    protected void setSelectedEntry(int index) {
        if (shouldEnable.test(this.getSelectedEntry())) {
            super.setSelectedEntry(index);
            selectedCallback.accept(this.getSelectedEntry());
        }
    }


    @Override
    //#if MC > 11502
    public void render(int mouseX, int mouseY, boolean selected, PoseStack matrixStack) {
        //#else
        //$$ public void render(int mouseX, int mouseY, boolean selected) {
        //#endif
        if (shouldEnable.test(this.getSelectedEntry())) {
            //#if MC > 11502
            super.render(mouseX, mouseY, selected, matrixStack);
            //#else
            //$$ super.render(mouseX, mouseY, selected);
            //#endif
        }
    }

}
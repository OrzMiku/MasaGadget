package com.plusls.MasaGadget.mixin.mod_tweak.malilib.favoritesSupport;

import com.plusls.MasaGadget.SharedConstants;
import com.plusls.MasaGadget.game.Configs;
import com.plusls.MasaGadget.impl.mod_tweak.malilib.favoritesSupport.MalilibFavoritesButton;
import com.plusls.MasaGadget.impl.mod_tweak.malilib.favoritesSupport.MalilibFavoritesData;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.GuiListBase;
import fi.dy.masa.malilib.gui.widgets.WidgetConfigOption;
import fi.dy.masa.malilib.gui.widgets.WidgetListConfigOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.hendrixshen.magiclib.api.i18n.I18n;
import top.hendrixshen.magiclib.util.collect.ValueContainer;

//#if MC > 11902 && MC < 12101
//$$ import org.spongepowered.asm.mixin.Intrinsic;
//#endif

@Mixin(value = GuiConfigsBase.class, remap = false, priority = 1100)
public abstract class MixinGuiConfigBase extends GuiListBase<GuiConfigsBase.ConfigOptionWrapper, WidgetConfigOption, WidgetListConfigOptions> {
    protected MixinGuiConfigBase(int listX, int listY) {
        super(listX, listY);
    }

    //#if MC > 11902 && MC < 12101
    //$$ @Intrinsic
    //$$ @Override
    //$$ public void initGui() {
    //$$     super.initGui();
    //$$ }
    //$$
    //$$ @SuppressWarnings({"MixinAnnotationTarget", "UnresolvedMixinReference"})
    //#endif

    //#if MC >= 12101
    //$$ @Override
    //$$ public void initGui() {
    //$$     super.initGui();
    //$$ }
    //$$
    //$$ @SuppressWarnings({"MixinAnnotationTarget", "UnresolvedMixinReference"})
    //#endif
    @Inject(method = "initGui", at = @At("RETURN"))
    public void postInitGui(CallbackInfo ci) {
        if (!Configs.favoritesSupport.getBooleanValue()) {
            return;
        }

        MalilibFavoritesButton favoritesButton = MalilibFavoritesButton.create(
                this.width - 132, 10, MalilibFavoritesData.getInstance().isFilterSwitch(),
                status -> {
                    MalilibFavoritesData.getInstance().setFilterSwitch(status);
                    ValueContainer.ofNullable(this.getListWidget()).ifPresent(w -> {
                        w.getScrollbar().setValue(0);
                        w.refreshEntries();
                    });
                    SharedConstants.getConfigHandler().save();
                },
                status -> status ? I18n.tr("masa_gadget_mod.message.showAllOptions") :
                        I18n.tr("masa_gadget_mod.message.showFavorite"));
        this.addWidget(favoritesButton);
    }
}

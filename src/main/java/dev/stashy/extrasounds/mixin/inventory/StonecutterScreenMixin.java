package dev.stashy.extrasounds.mixin.inventory;

import dev.stashy.extrasounds.SoundManager;
import net.minecraft.client.gui.screen.ingame.StonecutterScreen;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * For Stone cutter screen scroll sound.
 */
@Mixin(StonecutterScreen.class)
public abstract class StonecutterScreenMixin {
    @Unique
    private static final String FIELD_ID_SCROLL_OFFSET = "Lnet/minecraft/client/gui/screen/ingame/StonecutterScreen;scrollOffset:I";

    @Shadow
    private int scrollOffset;

    @Inject(
            method = "onInventoryChange",
            at = @At(
                    value = "FIELD",
                    target = FIELD_ID_SCROLL_OFFSET,
                    opcode = Opcodes.PUTFIELD
            )
    )
    private void extrasounds$stonecutterScreenReset(CallbackInfo ci) {
        SoundManager.resetScrollPos();
    }

    @Inject(
            method = { "mouseScrolled", "mouseDragged" },
            at = @At(
                    value = "FIELD",
                    target = FIELD_ID_SCROLL_OFFSET,
                    opcode = Opcodes.PUTFIELD,
                    shift = At.Shift.AFTER
            )
    )
    private void extrasounds$stonecuttorScreenScroll(CallbackInfoReturnable<Boolean> cir) {
        SoundManager.screenScroll(this.scrollOffset);
    }
}

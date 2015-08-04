package io.apptik.widget.multiview.animators;


public interface AnimationSetProvider {


    AnimationProvider getAddAnimProvider();
    AnimationProvider getRemoveAnimProvider();
    AnimationProvider getMoveAnimProvider();
    AnimationProvider getChangeOldItemAnimProvider();
    AnimationProvider getChangeNewItemAnimProvider();


}

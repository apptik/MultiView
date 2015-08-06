package io.apptik.widget.multiview.animators;


public interface AnimatorSetProvider {


    AnimatorProvider getAddAnimProvider();
    AnimatorProvider getRemoveAnimProvider();
    AnimatorProvider getMoveAnimProvider();
    AnimatorProvider getChangeOldItemAnimProvider();
    AnimatorProvider getChangeNewItemAnimProvider();


}

package io.apptik.widget.multiview.animators;


public class Sets {
    private Sets() {
        throw new IllegalStateException("no instances");
    }

    public static AnimationSetProvider defaultAnimationrSet() {
        return new AnimationSetProvider() {
            @Override
            public AnimationProvider getAddAnimProvider() {
                return Providers.defaultAddAnimProvider();
            }

            @Override
            public AnimationProvider getRemoveAnimProvider() {
                return Providers.defaultRemoveAnimProvider();
            }

            @Override
            public AnimationProvider getMoveAnimProvider() {
                return Providers.defaultMoveAnimProvider();
            }

            @Override
            public AnimationProvider getChangeOldItemAnimProvider() {
                return Providers.defaultChangeOldViewAnimProvider();
            }

            @Override
            public AnimationProvider getChangeNewItemAnimProvider() {
                return Providers.defaultChangeNewViewAnimeProvider();
            }
        };
    }
}

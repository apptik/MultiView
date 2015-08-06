package io.apptik.widget.multiview.animators;


public class Sets {
    private Sets() {
        throw new IllegalStateException("no instances");
    }

    public static AnimatorSetProvider defaultItemAnimatorSet() {
        return new AnimatorSetProvider() {
            @Override
            public AnimatorProvider getAddAnimProvider() {
                return Providers.defaultAddAnimProvider();
            }

            @Override
            public AnimatorProvider getRemoveAnimProvider() {
                return Providers.defaultRemoveAnimProvider();
            }

            @Override
            public AnimatorProvider getMoveAnimProvider() {
                return Providers.defaultMoveAnimProvider();
            }

            @Override
            public AnimatorProvider getChangeOldItemAnimProvider() {
                return Providers.defaultChangeOldViewAnimProvider();
            }

            @Override
            public AnimatorProvider getChangeNewItemAnimProvider() {
                return Providers.defaultChangeNewViewAnimProvider();
            }
        };
    }

    public static AnimatorSetProvider garageDoorSet() {
        return new AnimatorSetProvider() {
            @Override
            public AnimatorProvider getAddAnimProvider() {
                return Providers.garageDoorAddProvider();
            }

            @Override
            public AnimatorProvider getRemoveAnimProvider() {
                return Providers.garageDoorRemoveProvider();
            }

            @Override
            public AnimatorProvider getMoveAnimProvider() {
                return null;
            }

            @Override
            public AnimatorProvider getChangeOldItemAnimProvider() {
                return Providers.defaultChangeOldViewAnimProvider();
            }

            @Override
            public AnimatorProvider getChangeNewItemAnimProvider() {
                return Providers.defaultChangeNewViewAnimProvider();

            }
        };
    }
}

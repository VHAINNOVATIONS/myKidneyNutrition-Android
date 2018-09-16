package com.topcoder.vakidney.adapter;

import android.databinding.BaseObservable;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.topcoder.vakidney.BR;
import com.topcoder.vakidney.databinding.ItemAddMealDrugImageBinding;
import com.topcoder.vakidney.databinding.ItemMealDrugImageBinding;
import com.topcoder.vakidney.databinding.ItemMealDrugImageSmallBinding;
import com.topcoder.vakidney.model.MealDrugImage;

import java.util.ArrayList;
import java.util.List;

public class MealDrugImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<MealDrugImage> mealDrugImages = new ArrayList<>();
    private final OnMealDrugActions onMealDrugActions;

    public MealDrugImageAdapter(
            @NonNull List<MealDrugImage> mealDrugImages,
            @Nullable OnMealDrugActions onMealDrugActions) {
        this.onMealDrugActions = onMealDrugActions;
        this.mealDrugImages.addAll(mealDrugImages);
    }

    public MealDrugImageAdapter(@NonNull List<MealDrugImage> mealDrugImages) {
        this(mealDrugImages, null);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ViewType.VIEW_TYPE_ADD:
                ItemAddMealDrugImageBinding addMealDrugImageBinding =
                        ItemAddMealDrugImageBinding.inflate(inflater, parent, false);
                return new AddMealDrugImageViewHolder(addMealDrugImageBinding);
            case ViewType.VIEW_TYPE_IMAGE:
                if (onMealDrugActions == null) {
                    ItemMealDrugImageSmallBinding mealDrugImageBinding =
                            ItemMealDrugImageSmallBinding.inflate(inflater, parent, false);
                    return new MealDrugImageViewHolder(mealDrugImageBinding);
                } else {
                    ItemMealDrugImageBinding mealDrugImageBinding =
                            ItemMealDrugImageBinding.inflate(inflater, parent, false);
                    return new MealDrugImageViewHolder(mealDrugImageBinding);
                }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ViewType.VIEW_TYPE_ADD:
                AddMealDrugImageViewHolder addMealDrugImageViewHolder = (AddMealDrugImageViewHolder) holder;
                addMealDrugImageViewHolder.binding
                        .setViewModel(new AddMealDrugImageViewModel(onMealDrugActions));
                break;
            case ViewType.VIEW_TYPE_IMAGE:
                final MealDrugImageViewHolder mealDrugImageViewHolder = (MealDrugImageViewHolder) holder;
                View.OnClickListener onRemoveItemClickListener = null;
                ImageView addedImage;
                ViewDataBinding binding;
                if (mealDrugImageViewHolder.binding != null) {
                    binding = mealDrugImageViewHolder.binding;
                    addedImage = mealDrugImageViewHolder.binding.addedImage;
                    onRemoveItemClickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int position = mealDrugImageViewHolder.getAdapterPosition();
                            MealDrugImage mealDrugImage = mealDrugImages.remove(position - getItemCountOffset());
                            notifyItemRemoved(position);
                            onMealDrugActions.onRemoveMealDrugImage(mealDrugImage);
                        }
                    };
                } else {
                    binding = mealDrugImageViewHolder.smallBinding;
                    addedImage = mealDrugImageViewHolder.smallBinding.addedImage;
                }
                binding.setVariable(BR.viewModel, new MealDrugImageViewModel(onRemoveItemClickListener));
                MealDrugImage mealDrugImage = mealDrugImages.get(position - getItemCountOffset());
                if(!TextUtils.isEmpty(mealDrugImage.getUrl())) {
                    Glide.with(holder.itemView.getContext())
                            .load(mealDrugImage.getUrl())
                            .into(addedImage);
                }
                break;
        }
    }

    @Override
    @ViewType
    public int getItemViewType(int position) {
        return (position == 0 && onMealDrugActions != null) ? ViewType.VIEW_TYPE_ADD : ViewType.VIEW_TYPE_IMAGE;
    }

    @Override
    public int getItemCount() {
        return getItemCountOffset() + mealDrugImages.size();
    }

    public void addMealDrugImage(MealDrugImage mealDrugImage) {
        mealDrugImages.add(mealDrugImage);
        notifyItemInserted(mealDrugImages.size() + getItemCountOffset());
    }

    private int getItemCountOffset() {
        return onMealDrugActions == null ? 0 : 1;
    }

    class AddMealDrugImageViewHolder extends RecyclerView.ViewHolder {

        private ItemAddMealDrugImageBinding binding;

        public AddMealDrugImageViewHolder(ItemAddMealDrugImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class MealDrugImageViewHolder extends RecyclerView.ViewHolder {

        private ItemMealDrugImageBinding binding;
        private ItemMealDrugImageSmallBinding smallBinding;

        public MealDrugImageViewHolder(ItemMealDrugImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public MealDrugImageViewHolder(ItemMealDrugImageSmallBinding smallBinding) {
            super(smallBinding.getRoot());
            this.smallBinding = smallBinding;
        }
    }

    public static class AddMealDrugImageViewModel extends BaseObservable {

        private OnAddMealDrugImageAction onAddMealDrugImageAction;

        public AddMealDrugImageViewModel(OnAddMealDrugImageAction onAddMealDrugImageAction) {
            this.onAddMealDrugImageAction = onAddMealDrugImageAction;
        }

        public void onItemClick(View view) {
            if (onAddMealDrugImageAction == null) {
                return;
            }
            onAddMealDrugImageAction.onAddMealDrugImage();
        }
    }

    public static class MealDrugImageViewModel extends BaseObservable {

        @Nullable
        private View.OnClickListener onRemoveItemClickListener;

        public MealDrugImageViewModel(@Nullable View.OnClickListener onRemoveItemClickListener) {
            this.onRemoveItemClickListener = onRemoveItemClickListener;
        }

        public void onRemoveItemClick(View view) {
            if (onRemoveItemClickListener == null) {
                return;
            }
            onRemoveItemClickListener.onClick(view);
        }

    }

    public interface OnMealDrugActions
            extends OnAddMealDrugImageAction, OnRemoveMealDrugImageAction {


        void onRemoveMealDrugImage(MealDrugImage mealDrug);
    }

    public interface OnAddMealDrugImageAction {
        void onAddMealDrugImage();
    }

    public interface OnRemoveMealDrugImageAction {
        void onRemoveMealDrugImage(MealDrugImage mealDrug);
    }

    @interface ViewType {
        int VIEW_TYPE_ADD = 0;
        int VIEW_TYPE_IMAGE = 1;
    }
}
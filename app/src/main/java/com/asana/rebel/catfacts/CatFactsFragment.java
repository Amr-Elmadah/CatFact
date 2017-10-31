package com.asana.rebel.catfacts;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.asana.rebel.BaseNetworkFragment;
import com.asana.rebel.catfact.R;
import com.asana.rebel.customviews.DividerItemDecoration;
import com.asana.rebel.customviews.OnVerticalScrollListener;
import com.asana.rebel.customviews.RecyclerViewEmptySupport;
import com.asana.rebel.data.CatFactRepository;
import com.asana.rebel.data.models.Fact;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.asana.rebel.utils.Utils.checkNotNull;


public class CatFactsFragment extends BaseNetworkFragment implements CatFactsContract.View {


    @BindView(R.id.rv_cat_facts)
    RecyclerViewEmptySupport recyclerView;
    @BindView(R.id.srl_cat_facts)
    SwipeRefreshLayout swipeRefreshLayoutRecyclerview;
    @BindView(R.id.srl_cat_facts_emptyView)
    SwipeRefreshLayout swipeRefreshLayoutEmptyView;
    @BindView(R.id.tv_max_length)
    TextView tvMaxLength;
    @BindView(R.id.sb_length)
    SeekBar sbMaxLength;
    @BindView(R.id.ll_length)
    LinearLayout llLength;
    private CatFactsAdapter mAdapter;

    private CatFactsContract.Presenter mPresenter;

    private int mLength = 200, sbStepSize = 10;


    public CatFactsFragment() {
        // Required empty public constructor
    }

    public static CatFactsFragment newInstance() {
        CatFactsFragment notificationsFragment = new CatFactsFragment();
        Bundle args = new Bundle();
        notificationsFragment.setArguments(args);
        return notificationsFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mAdapter = new CatFactsAdapter(new ArrayList<Fact>(), new CatFactsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Fact fact) {
                mPresenter.onCatFactClicked(fact);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_cat_facts, container, false);
        ButterKnife.bind(this, mView);

        setupRecyclerView(recyclerView);
        setupSwipeRefreshLayout(swipeRefreshLayoutRecyclerview);
        setupSwipeRefreshLayout(swipeRefreshLayoutEmptyView);
        initLengthSeekBar();
        if (mPresenter == null) {
            mPresenter = new CatFactsPresenter(mLength, new CatFactRepository(), this);
        }

        if (savedInstanceState == null) {
            mPresenter.start();
        }

        return mView;
    }

    private void initLengthSeekBar() {
        sbMaxLength.setProgress(mLength);
        tvMaxLength.setText("" + mLength);
        sbMaxLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //To force the seek bar step size to $sbStepSize
                if (progress % sbStepSize == 0) {
                    mLength = progress;
                    mPresenter.loadCatFacts(mLength);
                    tvMaxLength.setText("" + mLength);
                } else {
                    progress = (Math.round(progress / sbStepSize)) * sbStepSize;
                    seekBar.setProgress(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setupSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadCatFacts(mLength);
            }
        });
    }

    private void setupRecyclerView(RecyclerViewEmptySupport recyclerView) {
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setEmptyView(swipeRefreshLayoutEmptyView);
        recyclerView.addOnScrollListener(new OnVerticalScrollListener(new OnVerticalScrollListener.ScrollDirectionCallback() {
            @Override
            public void onScrolledUp() {
                viewView(llLength);
            }

            @Override
            public void onScrolledDown() {
                hideView(llLength);
            }
        }));
        recyclerView.setAdapter(mAdapter);
    }


    private void hideView(final View view) {
        if (view.getVisibility() == View.VISIBLE) {
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_top_bottom);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.GONE);
                }
            });

            view.startAnimation(animation);
        }
    }

    private void viewView(final View view) {
        if (view.getVisibility() == View.GONE) {
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_bottom_top);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.VISIBLE);
                }
            });

            view.startAnimation(animation);
        }
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (swipeRefreshLayoutRecyclerview != null && swipeRefreshLayoutEmptyView != null) {
            swipeRefreshLayoutRecyclerview.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayoutRecyclerview.setRefreshing(active);
                    // Set refreshing to false on the empty view's SwipeRefreshLayout if it's active
                    if (swipeRefreshLayoutEmptyView.isRefreshing() && !active) {
                        swipeRefreshLayoutEmptyView.setRefreshing(false);
                    }
                }
            });
        } else {
            startActivity(new Intent(getActivity(), CatFactActivity.class));
            getActivity().finish();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showCatFacts(List<Fact> catFacts) {
        recyclerView.setLoaded(true);
        mAdapter.replaceData(catFacts);

    }

    @Override
    public void showClickedCatFact(Fact fact) {
        Toast.makeText(getActivity(), fact.getFact(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void setPresenter(CatFactsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public static class CatFactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int TYPE_ITEM = 1;

        private List<Fact> mCatFacts;
        private OnItemClickListener mOnItemClickListener;

        private boolean isLoading;

        private CatFactsAdapter(List<Fact> catFacts, OnItemClickListener onItemClickListener) {
            mOnItemClickListener = checkNotNull(onItemClickListener);
            setList(catFacts);
        }


        private void setLoaded() {
            isLoading = false;
        }

        public void replaceData(List<Fact> catFacts) {
            setList(catFacts);
            notifyDataSetChanged();
        }

        private void setList(List<Fact> catFacts) {
            mCatFacts = checkNotNull(catFacts);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            if (viewType == TYPE_ITEM) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cat_fact, viewGroup, false);
                return new ItemViewHolder(view, new ItemViewHolder.OnItemInteractionListener() {
                    @Override
                    public void onItemClick(int position) {
                        mOnItemClickListener.onItemClick(mCatFacts.get(position));
                    }
                });
            } else {
                throw new IllegalStateException("Not a valid type");
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
            if (viewHolder instanceof ItemViewHolder) {
                ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
                final Fact fact = mCatFacts.get(position);
                itemViewHolder.catFactName.setText(fact.getFact());
            }
        }

        @Override
        public int getItemViewType(int position) {
            return TYPE_ITEM;
        }

        @Override
        public int getItemCount() {
            return (null != mCatFacts ? mCatFacts.size() : 0);
        }

        public static class ItemViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.tv_cat_fact_title)
            TextView catFactName;

            private OnItemInteractionListener mOnItemInteractionListener;

            public ItemViewHolder(View view, OnItemInteractionListener onItemInteractionListener) {
                super(view);
                ButterKnife.bind(this, itemView);
                mOnItemInteractionListener = onItemInteractionListener;
            }

            @OnClick(R.id.ll_list_item)
            void itemClick() {
                mOnItemInteractionListener.onItemClick(getAdapterPosition());
            }

            interface OnItemInteractionListener {
                void onItemClick(int position);
            }
        }

        private interface OnItemClickListener {
            void onItemClick(Fact fact);
        }
    }
}
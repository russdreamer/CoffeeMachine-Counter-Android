package com.toolittlespot.coffeemachinecleancounter.businesslogic.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.text.format.DateFormat;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.toolittlespot.coffeemachinecleancounter.R;
import com.toolittlespot.coffeemachinecleancounter.businesslogic.application.User;
import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity;
import com.toolittlespot.coffeemachinecleancounter.uilogic.fragments.Statistics;

import java.util.ArrayList;
import java.util.List;


public class StatAdapter extends BaseAdapter implements Filterable, ThemedSpinnerAdapter {
    private final LayoutInflater mInflater;


    public List<Statistics.StatisticPOJO> mData;

    private int mResource;
    private int mDropDownResource;
    private AdapterType adapterType;

    /** Layout inflater used for {@link #getDropDownView(int, View, ViewGroup)}. */
    private LayoutInflater mDropDownInflater;

    private StatFilter mFilter;
    private ArrayList<Statistics.StatisticPOJO> mUnfilteredData;

    /**
     * Constructor
     *
     * @param context The context where the View associated with this StatAdapter is running
     * @param data A List of Maps. Each entry in the List corresponds to one row in the list. The
     *        Maps contain the data for each row, and should include all the entries specified in
     *        "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *        item. The layout file should include at least those named views defined in "to"
     */
    public StatAdapter(Context context, List<Statistics.StatisticPOJO> data,
                       @LayoutRes int resource, AdapterType type) {
        mData = data;
        mResource = mDropDownResource = resource;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        adapterType = type;
    }

    /**
     * @see Adapter#getCount()
     */
    public int getCount() {
        return mData.size();
    }

    /**
     * @see Adapter#getItem(int)
     */
    public Statistics.StatisticPOJO getItem(int position) {
        return mData.get(position);
    }

    /**
     * @see Adapter#getItemId(int)
     */
    public long getItemId(int position) {
        return mData.get(position).getUser().getId();
    }

    /**
     * @see Adapter#getView(int, View, ViewGroup)
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(mInflater, position, convertView, parent, mResource);
    }

    private View createViewFromResource(LayoutInflater inflater, int position, View convertView,
                                        ViewGroup parent, int resource) {
        View v;
        if (convertView == null) {
            v = inflater.inflate(resource, parent, false);
        } else {
            v = convertView;
        }

        bindView(position, v);
        return v;
    }

    /**
     * <p>Sets the layout resource to create the drop down views.</p>
     *
     * @param resource the layout resource defining the drop down views
     * @see #getDropDownView(int, View, ViewGroup)
     */
    public void setDropDownViewResource(int resource) {
        mDropDownResource = resource;
    }

    /**
     * Sets the {@link Resources.Theme} against which drop-down views are
     * inflated.
     * <p>
     * By default, drop-down views are inflated against the theme of the
     * {@link Context} passed to the adapter's constructor.
     *
     * @param theme the theme against which to inflate drop-down views or
     *              {@code null} to use the theme from the adapter's context
     * @see #getDropDownView(int, View, ViewGroup)
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void setDropDownViewTheme(Resources.Theme theme) {
        if (theme == null) {
            mDropDownInflater = null;
        } else if (theme == mInflater.getContext().getTheme()) {
            mDropDownInflater = mInflater;
        } else {
            final Context context = new ContextThemeWrapper(mInflater.getContext(), theme);
            mDropDownInflater = LayoutInflater.from(context);
        }
    }

    @Override
    public Resources.Theme getDropDownViewTheme() {
        return mDropDownInflater == null ? null : mDropDownInflater.getContext().getTheme();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = mDropDownInflater == null ? mInflater : mDropDownInflater;
        return createViewFromResource(inflater, position, convertView, parent, mDropDownResource);
    }

    private void bindView(int position, View view) {
        final Statistics.StatisticPOJO stat = mData.get(position);
        if (stat == null) {
            return;
        }

        long userID = stat.getUser().getId();
        User user = MainActivity.app.getUser(userID);

        ImageView avatarView = view.findViewById(R.id.avatar_column);
        TextView nameView = view.findViewById(R.id.name_column);
        TextView quantityView = view.findViewById(R.id.quantity_column);

        nameView.setText(user.getName());

        if (this.adapterType == AdapterType.USE)
            quantityView.setText(String.valueOf(stat.getUseTimes()));
        else quantityView.setText(String.valueOf(stat.getCleanTimes()));

        avatarView.setImageURI(Uri.parse(user.getAvatarPath()));
    }

    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new StatFilter();
        }
        return mFilter;
    }

    public enum AdapterType{
        USE,
        CLEAN
    }

    /**
     * <p>An array filters constrains the content of the array adapter with
     * a prefix. Each item that does not start with the supplied prefix
     * is removed from the list.</p>
     */
    private class StatFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mUnfilteredData == null) {
                mUnfilteredData = new ArrayList<>(mData);
            }

            if (prefix == null || prefix.length() == 0) {
                ArrayList<Statistics.StatisticPOJO> list = mUnfilteredData;
                results.values = list;
                results.count = list.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();

                ArrayList<Statistics.StatisticPOJO> unfilteredValues = mUnfilteredData;
                int count = unfilteredValues.size();

                ArrayList<Statistics.StatisticPOJO> newValues = new ArrayList<>(count);

                for (int i = 0; i < count; i++) {
                    Statistics.StatisticPOJO stat = unfilteredValues.get(i);
                    if (stat != null) {

                        String userName = stat.getUser().getName();
                        String[] words =  userName.split(" ");

                        for (String word : words) {
                            if (word.toLowerCase().startsWith(prefixString)) {
                                newValues.add(stat);
                                break;
                            }
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //noinspection unchecked
            mData = (List<Statistics.StatisticPOJO>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}

package com.example.rhalwai.velenton;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.rhalwai.velenton.more.MoreList;
import com.example.rhalwai.velenton.more.MoreListAdapter;

import java.util.ArrayList;

public class MoreTab4 extends android.support.v4.app.Fragment {

    ArrayList<MoreList> moreLists;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab4_more, container, false);

        moreLists = new ArrayList<>();
        listView = rootView.findViewById(R.id.moreListView);
        moreLists.add(new MoreList(R.drawable.my_order, "My Order"));
        moreLists.add(new MoreList(R.drawable.news_update, "News Update"));
        moreLists.add(new MoreList(R.drawable.payment_module, "Payment Module"));
        moreLists.add(new MoreList(R.drawable.bank_details, "My Bank Details"));
        moreLists.add(new MoreList(R.drawable.sell_with_us, "Sell With Us"));
        moreLists.add(new MoreList(R.drawable.rate_us, "Rate us"));
        moreLists.add(new MoreList(R.drawable.share, "Share App"));
        moreLists.add(new MoreList(R.drawable.refund, "Return and Refund Policy"));
        moreLists.add(new MoreList(R.drawable.logout, "Logout"));
        moreLists.add(new MoreList(R.drawable.faq, "FAQs"));

        MoreListAdapter adapter = new MoreListAdapter(moreLists, getActivity());
        listView.setAdapter(adapter);

        return rootView;
    }
}

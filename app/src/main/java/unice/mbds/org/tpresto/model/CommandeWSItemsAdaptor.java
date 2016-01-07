package unice.mbds.org.tpresto.model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import unice.mbds.org.tpresto.R;

/**
 * Created by Zac on 28/12/2015.
 */
public class CommandeWSItemsAdaptor extends BaseAdapter {
    List<CommandeWS> listCmdWS;
    Context context;

    public CommandeWSItemsAdaptor(Context context, List<CommandeWS> listCmdWS) {
        this.context=context;
        this.listCmdWS = listCmdWS;
    }

    @Override
    public int getCount() {
        return listCmdWS.size();
    }

    @Override
    public Object getItem(int position) {
        return listCmdWS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        CommandeWSViewHolder viewHolder = null;

        if (v==null){
            v = View.inflate(context, R.layout.layout_get_commande_ws,null);
            viewHolder = new CommandeWSViewHolder();
            viewHolder.price = (TextView) v.findViewById(R.id.price_commande_ws);
            viewHolder.disscount = (TextView) v.findViewById(R.id.discount_commande_ws);
            viewHolder.server = (TextView) v.findViewById(R.id.server_commande_ws);
            viewHolder.cooker = (TextView) v.findViewById(R.id.cooker_commande_ws);
            viewHolder.items = (TextView) v.findViewById(R.id.items_commande_ws);
            v.setTag(viewHolder);
        }
        else{
            viewHolder = (CommandeWSViewHolder) v.getTag();
        }

        CommandeWS cmdsws = listCmdWS.get(position);
        viewHolder.price.setText("Prix : "+cmdsws.getPrice());
        viewHolder.disscount.setText("Discount : "+cmdsws.getDiscount());
        viewHolder.server.setText("Serveur : "+cmdsws.getServer().getId());
        viewHolder.cooker.setText("Cooker : "+cmdsws.getCooker().getId());
        String str = "";
        for (int i=0;i<cmdsws.getItems().size();i++)
            str = str+"\n  id : "+cmdsws.getItems().get(i).getId();
        viewHolder.items.setText("Contenu Ordre : "+str);
        return v;
    }

    class CommandeWSViewHolder{
        TextView price;
        TextView disscount;
        TextView server;
        TextView cooker;
        TextView items;
    }
}

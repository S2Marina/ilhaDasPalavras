package marina.jogo.control;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import marina.jogo.R;


public class Mapa2 extends Fragment {

    TextView silaba1;
    TextView silaba2;
    TextView silaba3;
    String s1;
    String s2;
    String s3;
    String dica;
    String dica2;
    TextView d;
    TextView d2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mapa_2, container, false);
        setHasOptionsMenu(true);

        s1 = (String) getArguments().getSerializable("s1");
        s2 = (String) getArguments().getSerializable("s2");
        s3 = (String) getArguments().getSerializable("s3");
        dica = (String) getArguments().getSerializable("dica");
        dica2 = (String) getArguments().getSerializable("dica2");

        silaba1 = (TextView) view.findViewById(R.id.mapa2_s1);
        silaba2 = (TextView) view.findViewById(R.id.mapa2_s2);
        silaba3 = (TextView) view.findViewById(R.id.mapa2_s3);
        d = (TextView) view.findViewById(R.id.mapa2_dica);
        d2 = (TextView) view.findViewById(R.id.mapa2_dica2);

        MainActivity.t.setTitle("Mapa");
        MainActivity.t.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        MainActivity.t.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        d.setText(dica);
        d2.setText(dica2);

        if(s1 == null){
            silaba1.setText("");
        }
        else{
            silaba1.setText(s1.toString());
        }

        if (s2 == null){
            silaba2.setText("");
        }
        else{
            silaba2.setText(s2.toString());
        }

        if (s3 == null){
            silaba3.setText("");
        }
        else{
            silaba3.setText(s3.toString());
        }




        return view;
    }
}

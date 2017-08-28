package marina.jogo.control;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import marina.jogo.R;

public class Mapa1 extends Fragment{

    TextView silaba1;
    TextView silaba2;
    TextView d;
    TextView d2;
    String s1;
    String s2;
    String dica;
    String dica2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mapa_1, container, false);
        setHasOptionsMenu(true);

        s1 = (String) getArguments().getSerializable("s1");
        s2 = (String) getArguments().getSerializable("s2");
        dica = (String) getArguments().getSerializable("dica");
        dica2 = (String) getArguments().getSerializable("dica2");


        MainActivity.t.setTitle("Mapa");
        MainActivity.t.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        MainActivity.t.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        silaba1 = (TextView) view.findViewById(R.id.mapa1_s1);
        silaba2 = (TextView) view.findViewById(R.id.mapa1_s2);
        d = (TextView) view.findViewById(R.id.mapa1_dica);
        d2 = (TextView) view.findViewById(R.id.mapa1_dica2);

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


        return view;
    }
}

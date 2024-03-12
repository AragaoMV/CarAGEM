package com.marcos.project1andorid2.Util;


import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.marcos.project1andorid2.R;
import com.marcos.project1andorid2.model.Carro;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class MainAdapter extends FirebaseRecyclerAdapter<Carro,MainAdapter.myViewHolder> {
    public MainAdapter(@NonNull FirebaseRecyclerOptions<Carro> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull Carro model) {
        holder.marca.setText(model.getMarca());
        holder.modelo.setText(model.getModelo());
        holder.ano.setText(model.getAno());
        holder.cor.setText(model.getCor());
        holder.placa.setText(model.getPlaca());
        holder.nomeProprietario.setText(model.getNomeProprietario());
        holder.obs.setText(model.getObs());

        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.nomeProprietario.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_layout))
                        .setExpanded(true,1600).create();

                View view = dialogPlus.getHolderView();

                EditText modelo = view.findViewById(R.id.etUpdModelo);
                EditText marca = view.findViewById(R.id.etUpdMarca);
                EditText ano = view.findViewById(R.id.etUpdAno);
                EditText cor = view.findViewById(R.id.etUpdCor);
                EditText placa = view.findViewById(R.id.etUpdPlaca);
                EditText nomeProp = view.findViewById(R.id.etUpdNomeProprietario);
                EditText obs = view.findViewById(R.id.etUpdObs);
                Button btnUpdate = view.findViewById(R.id.btnUpdateCarro);

                modelo.setText(model.getModelo());
                marca.setText(model.getMarca());
                ano.setText(model.getAno());
                cor.setText(model.getCor());
                placa.setText(model.getPlaca());
                nomeProp.setText(model.getNomeProprietario());
                obs.setText(model.getObs());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("ano",ano.getText().toString());
                        map.put("cor",cor.getText().toString());
                        map.put("marca",marca.getText().toString());
                        map.put( "modelo",modelo.getText().toString());
                        map.put("nomeProprietario",nomeProp.getText().toString());
                        map.put("obs",obs.getText().toString());
                        map.put("placa",placa.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("cars").child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(holder.nomeProprietario.getContext(), "Dados Atualizados com sucesso", Toast.LENGTH_SHORT).show();
                                dialogPlus.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.nomeProprietario.getContext(), "Erro ao atualizar os dados", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });

                    }
                });

            }
        });

        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.nomeProprietario.getContext());
                builder.setTitle("Deseja apagar este carro?");
                builder.setMessage("Após apagar, não será possivel recuperar");
                builder.setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("cars")
                                .child(getRef(position).getKey()).removeValue();
                        Toast.makeText(holder.nomeProprietario.getContext(), "Carro Deletado com Sucesso", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.nomeProprietario.getContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView marca,modelo,ano, cor, placa, nomeProprietario, obs;
        Button Edit, Delete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            marca = itemView.findViewById(R.id.lista_marca_text);
            modelo = itemView.findViewById(R.id.lista_modelo_text);
            ano = itemView.findViewById(R.id.lista_ano_text);
            cor = itemView.findViewById(R.id.lista_cor_text);
            placa = itemView.findViewById(R.id.lista_placa_text);
            nomeProprietario = itemView.findViewById(R.id.lista_proprietario_text);
            obs = itemView.findViewById(R.id.lista_obs_text);
            Edit = itemView.findViewById(R.id.btnEdit);
            Delete = itemView.findViewById(R.id.btnDelete);

        }
    }
}

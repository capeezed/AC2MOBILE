package com.facens.ac2melissagabriel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class TreinoAdapter extends RecyclerView.Adapter<TreinoAdapter.ViewHolder> {

    private Context context;
    private List<Treino> treinos;

    public interface OnItemClickListener {
        void onItemClick(Treino treino);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public TreinoAdapter(Context context, List<Treino> treinos) {
        this.context = context;
        this.treinos = treinos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_treino, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Treino t = treinos.get(position);

        holder.txtNome.setText("Treino: " + t.getNomeTreino());
        holder.txtData.setText("Data: " + t.getData());
        holder.txtDuracao.setText("Duração: " + t.getDuracao());
        holder.txtAtividade.setText("Atividade: " + t.getAtividade());
        holder.txtIntensidade.setText("Intensidade: " + t.getIntensidade());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(t);
            }
        });

        holder.itemView.setOnTouchListener(new View.OnTouchListener() {

            private long lastClickTime = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    long currentTime = System.currentTimeMillis();

                    if (currentTime - lastClickTime < 300) {
                        deletarTreino(t.getId(), holder.getAdapterPosition(), v);
                    }

                    lastClickTime = currentTime;
                }

                return false;
            }
        });
    }

    private void deletarTreino(String idDocumento, int position, View view) {

        FirebaseFirestore.getInstance()
                .collection("treinos")
                .document(idDocumento)
                .delete()
                .addOnSuccessListener(aVoid -> {

                    treinos.remove(position);

                    notifyItemRemoved(position);

                    Toast.makeText(
                            view.getContext(),
                            "Treino deletado!",
                            Toast.LENGTH_SHORT
                    ).show();
                })
                .addOnFailureListener(e -> {

                    Toast.makeText(
                            view.getContext(),
                            "Erro ao deletar",
                            Toast.LENGTH_SHORT
                    ).show();
                });
    }

    @Override
    public int getItemCount() {
        return treinos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNome;
        TextView txtData;
        TextView txtDuracao;
        TextView txtAtividade;
        TextView txtIntensidade;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNome = itemView.findViewById(R.id.txtNome);
            txtData = itemView.findViewById(R.id.txtData);
            txtDuracao = itemView.findViewById(R.id.txtDuracao);
            txtAtividade = itemView.findViewById(R.id.txtAtividade);
            txtIntensidade = itemView.findViewById(R.id.txtIntensidade);
        }
    }
}
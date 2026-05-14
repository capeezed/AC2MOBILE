package com.facens.ac2melissagabriel;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private EditText edtTreino, edtData, edtDuracao;
    private Spinner spinnerAtividade, spinnerIntensidade;
    private ListView listaTreino;
    private Button button;
    private CheckBox checkBox;
    private List<Treino> listaTreinos = new ArrayList<>();
    private TreinoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        adapter = new TreinoAdapter(this, listaTreinos);
        listaTreino.setAdapter(adapter);
        edtTreino = findViewById(R.id.edtTreino);
        edtDuracao = findViewById(R.id.edtDuracao);
        edtData = findViewById(R.id.edtData);
        spinnerAtividade = findViewById(R.id.spinnerAtividade);
        spinnerIntensidade = findViewById(R.id.spinnerIntensidade);
        listaTreino = findViewById(R.id.listaTreino);

        findViewById(R.id.button).setOnClickListener(v -> salvarTreino());

        carregarTreinos();

    }

    private void carregarTreinos(){
        db.collection("treinos")
                .get()
                .addOnSuccessListener(query -> {
                    listaTreinos.clear();
                    for(QueryDocumentSnapshot doc : query) {
                        Treino t = doc.toObject(Treino.class);
                        t.setId(doc.getId());
                        listaTreinos.add(t);
                    }
                    adapter.notifyDataSetChanged();
                });

        adapter.setOnItemClickListener(treino -> {
            edtTreino.setText(treino.getNomeTreino());
            spinnerAtividade.getSelectedItem().toString();
            edtData.setText(treino.getData());
            edtDuracao.setText(treino.getDuracao());
            spinnerIntensidade.getSelectedItem().toString();
            ((Button) findViewById(R.id.button)).setText("Atualizar Treino");
        });
    }

    private void limparCampos() {
        edtTreino.setText("");
        edtData.setText("");
        edtDuracao.setText(0);
        ((Button) findViewById(R.id.button)).setText("Salvar treinos");
    }

    private void salvarTreino(){
        String nomeTreino = edtTreino.getText().toString();
        String data = edtData.getText().toString();
        String duracao = edtDuracao.getText().toString();
        String Atividade = spinnerAtividade.getSelectedItem().toString();
        String Intensidade = spinnerIntensidade.getSelectedItem().toString();
        Treino treino = new Treino(null, nomeTreino, data, duracao, Atividade, Intensidade);
        db.collection("treinos")
                .add(treino)
                .addOnSuccessListener(doc -> {
                    treino.setId(doc.getId());
                    Toast.makeText(this, "Treino Salvo!", Toast.LENGTH_SHORT).show();
                    limparCampos();
                    carregarTreinos();
                });

    }
}

package guitheodoro.gmail.com.myworkapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class MyWorkPerfil extends AppCompatActivity{
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage sto = FirebaseStorage.getInstance();
    private StorageReference stoRef = sto.getReference();
    private ArrayList<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work_main);

        getUsrData();
        btnAdd();
    }

    //region User_Data
    public void getUsrData(){
        DocumentReference docRef = db.collection("cities").document("SF");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        String nome =  document.getData().get("Name").toString();
                        String email =  document.getData().get("Email").toString();
                        String nascimento =  document.getData().get("Nascimento").toString();
                        String idade =  document.getData().get("Idade").toString();
                        String telefone =  document.getData().get("Telefone").toString();
                        String sexo =  document.getData().get("Sexo").toString();

                        dataList.add(nome);
                        dataList.add(email);
                        dataList.add(nascimento);
                        dataList.add(idade);
                        dataList.add(telefone);
                        dataList.add(sexo);

                        setPerfil();
                        getFoto(email);
                    }
                }
            }
        });
    }

    private void getFoto(String _email){
        final long ONE_MEGABYTE = 1024 * 1024;
        final StorageReference userFoto = stoRef.child("user_fotos/" + _email + ".png");

        userFoto.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });
    }

    private void setPerfil(){
        TextView nome = findViewById(R.id.lblNome);
        TextView email = findViewById(R.id.lblEmail);
        TextView nascimento = findViewById(R.id.lblDdN);
        TextView idade = findViewById(R.id.lbl_Idade);
        TextView telefone = findViewById(R.id.lblTelefone);
        TextView sexo = findViewById(R.id.lblSexo);

        nome.setText(dataList.get(0));
        email.setText(dataList.get(1));
        nascimento.setText(dataList.get(2));
        idade.setText(dataList.get(3));
        telefone.setText(dataList.get(4));
        sexo.setText(dataList.get(5));
    }

    private void setNewFoto(Bitmap foto){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        foto.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        StorageReference userFoto = stoRef.child("user_fotos/" + dataList.get(1));

        UploadTask uploadTask = userFoto.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Snackbar.make(findViewById(R.id.MyWorkLoginLayout), "Houve algum erro!", LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Snackbar.make(findViewById(R.id.MyWorkLoginLayout), "Upload feito com sucesso!", LENGTH_LONG).show();
            }
        });
    }

    private void btnAdd(){
        Button fab = findViewById(R.id.btnAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_OK);
            }
        });
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                setNewFoto(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Snackbar.make(findViewById(R.id.MyWorkLoginLayout), "Houve algum erro!", LENGTH_LONG).show();
            }

        }else {
            Snackbar.make(findViewById(R.id.MyWorkLoginLayout), "Você não escolheu a foto!", LENGTH_LONG).show();
        }
    }

    public void Click(View v){
        switch(v.getId()) {
            case R.id.btnAdd:
                btnAdd();
        }
    }
    //endregion
}

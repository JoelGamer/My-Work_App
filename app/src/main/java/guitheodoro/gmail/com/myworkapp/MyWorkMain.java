package guitheodoro.gmail.com.myworkapp;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class MyWorkMain extends AppCompatActivity {

    //region Global_Var`s
    public ArrayAdapter<String> adapter = null;
    public ArrayAdapter<Bitmap> adapterImg = null;
    public ArrayList<String> dataList = new ArrayList<>();
    public ArrayList<Bitmap> dataListImg = new ArrayList<>();
    private int selection = 0;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage dbImg = FirebaseStorage.getInstance();
    private FirebaseStorage sto = FirebaseStorage.getInstance();
    private StorageReference stoRef = sto.getReference();
    private StorageReference stRef = dbImg.getReference();
    private SwipeRefreshLayout mySwipeRefreshLayout;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work_main);

        BottomNavigationView navigation = findViewById(R.id.nav_menu);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.nav_main);

        getValues(1);
    }

    private void getValues(int selection) {
        dataList.clear();

        if(selection == 1){
            db.collection("Test").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = document.getData().get("Name").toString();
                            dataList.add(name);
                        }
                    }
                }
            });
            mostrarEmpresas();
        }
        else if(selection == 2){
            db.collection("Notificacoes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = document.getData().get("Name").toString();
                            dataList.add(name);
                        }
                    }
                }
            });
            mostrarNotf();
        }
        else{

            db.collection("Chat").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = document.getData().get("Name").toString();
                            dataList.add(name);
                        }
                    }
                }
            });
            mostrarChat();
        }
    }

    private void mostrarEmpresas(){
        ListView lstMain = findViewById(R.id.lstMain);

        if(dataList.size() != 0 && dataListImg.size() != 0) {
            adapter = new ArrayAdapter<>(this, R.layout.my_work_main_list, R.id.Itemname, dataList);
            lstMain.setAdapter(adapter);
        }
        else{
            callBackError();
        }
    }

    private void mostrarChat(){
        if(dataList.size() != 0) {
            adapter = new ArrayAdapter<>(this, R.layout.my_work_main_list, R.id.Itemname, dataList);
        }
        else{
            callBackError();
        }
    }

    private void mostrarNotf(){
        if(dataList.size() != 0) {
            adapter = new ArrayAdapter<>(this, R.layout.my_work_main_list, R.id.Itemname, dataList);
        }
        else {
            callBackError();
        }
    }

    private void callBackError(){
        ListView lstMain = findViewById(R.id.lstMain);

        ArrayList<String> erro = new ArrayList<>();
        erro.add("Impossivel pegar os dados!");
        adapter = new ArrayAdapter<>(this, R.layout.my_work_main_list, R.id.Itemname, erro);
        lstMain.setAdapter(adapter);
    }

    private void Refresh(){
        mySwipeRefreshLayout.setRefreshing(false);
        getValues(selection);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            SwipeRefreshLayout constSwipe = findViewById(R.id.swipe_refresh);
            ConstraintLayout constraint = findViewById(R.id.Perfil);
            switch (item.getItemId()) {
                case R.id.nav_main:
                    constSwipe.setVisibility(View.VISIBLE);
                    constraint.setVisibility(View.INVISIBLE);
                    selection = 1;
                    getValues(selection);
                    return true;
                case R.id.nav_chat:
                    constSwipe.setVisibility(View.INVISIBLE);
                    constraint.setVisibility(View.INVISIBLE);
                    selection = 3;
                    //getValues(selection);
                    return true;
                case R.id.nav_notf:
                    constSwipe.setVisibility(View.INVISIBLE);
                    constraint.setVisibility(View.INVISIBLE);
                    selection = 2;
                    //getValues(selection);
                    return true;
                case R.id.nav_curr:
                    constSwipe.setVisibility(View.INVISIBLE);
                    constraint.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.nav_perf:
                    constSwipe.setVisibility(View.INVISIBLE);
                    constraint.setVisibility(View.VISIBLE);
                    createFragment();
                    return true;
            }
            return false;
        }
    };

    private void createFragment(){
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
        Button btn = findViewById(R.id.btnAdd);
        switch(v.getId()) {
            case R.id.btnAdd:
                if(btn.getVisibility() == View.VISIBLE){
                    btn.setVisibility(View.INVISIBLE);
                }else{
                    btn.setVisibility(View.VISIBLE);
                }
        }
    }
    //endregion

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_nav, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent nav_set = null;
        switch (item.getItemId()) {
            case R.id.nav_settings:
                nav_set = new Intent(this, MyWorkSettings.class);
                break;
            case R.id.nav_autor:
                SnackbarCreate();
            case R.id.swipe_refresh:
                mySwipeRefreshLayout.setRefreshing(true);
                Refresh();
        }
        if(nav_set != null){
            startActivity(nav_set);
        }
        return super.onOptionsItemSelected(item);
    }

    private void SnackbarCreate(){
        Snackbar.make(findViewById(R.id.MyWorkMainLayout), "Ainda não tem a autoria!", LENGTH_LONG).show();
    }
}

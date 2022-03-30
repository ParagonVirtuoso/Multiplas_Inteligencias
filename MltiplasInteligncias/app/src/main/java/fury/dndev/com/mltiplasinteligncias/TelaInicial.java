package fury.dndev.com.mltiplasinteligncias;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TelaInicial extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    //Recuperar a instancia do banco de dados do firebase
    //assim conseguindo fazer alteraçoes diretas no banco de dados
    //dando acesso ao firebaseDatabase
    //o get reference volta ao nó da raiz
    private DatabaseReference firebaseReferencia = FirebaseDatabase.getInstance().getReference();
     private DatabaseReference usuarioReferencia = firebaseReferencia.child("usuarios");
    private Button botao;
    private TextView pergunta;
    private ImageView imagemIlust;
    private int contador = 0;
    //contador que joga o aplicativo na tela que o usuario deixou
    private int estadoAtualEtapa = 0;
    private int estadoAtualEtapaR = 0;
    private int estadoAtualPergunta = 0;
    //checkbox para verificar se leu os termos
    private CheckBox checkTermos;
    //botao para sair da tela de termos
    private Button botaoTermos;
    //ScrollView para ocultar os termos apos confirmar
    private ScrollView scrollViewTermos;
    //SeekBar para escolher o nivel desejado
    private SeekBar opcoesSeek;
    //textview para mostrar a pergunta da etapa atual
    private TextView etapaAtual;
    //botoes para voltar nas perguntas
    private ImageButton botaoEtapa1;
    private ImageButton botaoEtapa2;
    private ImageButton botaoEtapa3;
    private ImageButton botaoEtapa4;
    private ImageButton botaoEtapa5;

    //salvar perguntas respondidas atualmente
    private ImageButton botaoEtapa6;
    private ImageButton botaoEtapa7;
    private int notaPergunta1 ;
    private int notaPergunta2;
    private int notaPergunta3;
    private int notaPergunta4;
    private int notaPergunta5;
    private int notaPergunta6;



    //os layouts para mostrar de acordo com as funçoes escolhidas
    private int notaPergunta7;
    private int perguntaVerificada;
    private ConstraintLayout lResultId;
    private ConstraintLayout lPerguntasId;
    private ConstraintLayout lTermosId;
    private ConstraintLayout lSobreId;
    private ConstraintLayout lEquipeId;


    //banco sql
    private ConstraintLayout lTermosSubId;
    private ConstraintLayout lLoadingId;
    private SQLiteDatabase bancoDados;
// paradinhas mostrar infos logar e deslogar
    private ImageView avatarId;
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView idTextView;
    //variaveis com os dados
    private String nomeUserC;
    private String emailUserC;
    private String idUserC;
    private GoogleApiClient googleApiClient;
//testando um metodo
    private boolean corrigir=false;
    private boolean lib;
    private Uri link;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private boolean finalizado = false;
    private int gerRespostaPerg1;
    private int gerRespostaPerg2;
    private int gerRespostaPerg3;
    private int gerRespostaPerg4;
    private int gerRespostaPerg5;
    private int gerRespostaPerg6;
    private int gerRespostaPerg7;
    private boolean term = false;
    private boolean onVerStatemant = false;
    private boolean lkver = true;
    private LinearLayout stasis;
    private  LinearLayout inEmoj1;
    private  LinearLayout inEmoj2;
    private  LinearLayout inEmoj3;
    private  LinearLayout inEmoj4;
    private  LinearLayout inEmoj5;
    private  LinearLayout inEmoj6;
    private  LinearLayout inEmoj7;
    private ConstraintLayout layoutComp;
    private EditText edNome;
    private Button confNome;
    private Button refazerBtn;
    private TextView textInteliResult;
    private TextView textInteliResult2;
    private boolean passed = false;
    private boolean passed2 = false;
    private boolean exibido = true;
    private int dotscount;
    private ImageView[] dots;
    private boolean suc = true;
    private boolean passT = false;
    private boolean contafaz = false;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        avatarId = findViewById(R.id.perfilFotoId);
        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);

        Glide.with(this).load(link).into(avatarId);
        nameTextView.setText(nomeUserC);
        emailTextView.setText(emailUserC);
        ocultarLayout(7);
        recuperarTarefas();


            estadoAtualEtapa = estadoAtualEtapaR;

        if ( onVerStatemant == true && estadoAtualEtapa == 0  ){

            ocultarLayout(2);

        }

        if (onVerStatemant == true && estadoAtualEtapa > 0){
        atualizarPergunta();
            }





        return super.onPrepareOptionsMenu(menu);
    }

    private void tutomos() {


            TapTargetView.showFor(this,                 // `this` is an Activity
                    TapTarget.forView(findViewById(R.id.refazerBtId), "Refazer o teste", "Clique aqui se desejar refazer o teste")
                            .tintTarget(false)
                            .outerCircleColor(R.color.azulgrad)
                            .titleTextColor(R.color.white)
                            .titleTextSize(24)
                            .targetRadius(80)
                            .descriptionTextColor(R.color.black)
                            .textTypeface(Typeface.SANS_SERIF)
                            .dimColor(R.color.black)
                            .drawShadow(true)
                            .cancelable(false)
                            .transparentTarget(false),
                    new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                        @Override
                        public void onTargetClick(TapTargetView view) {
                            super.onTargetClick(view);      // This call is optional
                                usuarioReferencia.child(idUserC).child("TUTORIAIS").child("REFAZER").setValue("FEITO");
                        }});





    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
        //remover o titulo
        this.setTitle("");





        viewPager = findViewById(R.id.viewPager);

        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];
        for(int i = 0 ; i< dotscount ; i++){

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8,1,8,0);

            sliderDotspanel.addView(dots[i], params);

        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0 ; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




/*
        DatabaseReference totalReferencia = firebaseReferencia.child("usuarios");

        try {

            totalReferencia.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(idUserC).child("TOTAL").exists()) {
                        estadoAtualEtapa = 8;

                        Log.i("RTFIREBASE", dataSnapshot.child("inteligencia 1").getValue().toString());
                        Log.i("RTFIREBASE", dataSnapshot.child("inteligencia 2").getValue().toString());
                        Log.i("RTFIREBASE", dataSnapshot.child("inteligencia 3").getValue().toString());
                        Log.i("RTFIREBASE", dataSnapshot.child("inteligencia 4").getValue().toString());
                        Log.i("RTFIREBASE", dataSnapshot.child("inteligencia 5").getValue().toString());
                        Log.i("RTFIREBASE", dataSnapshot.child("inteligencia 6").getValue().toString());
                        Log.i("RTFIREBASE", dataSnapshot.child("inteligencia 7").getValue().toString());
                    }
                    if (estadoAtualEtapa == 0  ){

                        ocultarLayout(2);
                    }


                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });}catch(Exception e){
            e.printStackTrace();
        }



*/


        //text view para mostrar a etapa atual
        etapaAtual = findViewById(R.id.etapaAtualId);


        ///paradinhas mostrar infos logar e delogar junto com imagem



        NavigationView navigationView = findViewById(R.id.nav_view);
        //View headerView = navigationView.getHeaderView(0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        //fazer um evento para quando o usuario abrir o menu

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                usuarioReferencia.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                            if(estadoAtualEtapa > 1 && dataSnapshot.child(idUserC).child("TUTORIAIS").child("REFAZER").exists()){
                            refazerBtn = findViewById(R.id.refazerBtId);
                            refazerBtn.setVisibility(View.VISIBLE);


                        }else { if(estadoAtualEtapa > 1){
                            refazerBtn = findViewById(R.id.refazerBtId);
                            refazerBtn.setVisibility(View.VISIBLE);
                                tutomos();}

                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);







            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

            };
        };

        //

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);







      //  View hView = navigationView.inflateHeaderView(R.layout.nav_header_tela_inicial);
        //ImageView imgvw = hView.findViewById(R.id.imageView);
        //TextView tv = hView.findViewById(R.id.textView);
        //imgvw.setImageResource(R.drawable.ic_menu_send);
       // tv.setText("FUNFOU");
       //final TextView nameTextView = hView.findViewById(R.id.nameTextView);
       //final TextView emailTextView =hView.findViewById(R.id.emailTextView);
       //final TextView idTextView = hView.findViewById(R.id.idTextView);
        // = hView.findViewById(R.id.perfilFotoId);

        //Glide.with(this).load(link).into(avatar);



        imagemIlust = findViewById(R.id.imagemId);

        //Log.i("o link", link.toString());

        //Picasso.get().load(link).into(fotoPerfil);


//Glide.parse(link).into(R.id.perfilFotoId);

      //Glide.with(this).load(link).into(fotoPerfil);






       //nameTextView.setText("QUI BELEX");
            //--nameTextView.setText(nomeUserC);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(TelaInicial.this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        firebaseAuth = firebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    setUserData(user);

                }else{
                    goLogInScreen();
                }

            }
        };






        //metodo para adcionar um listener para a referencia //o listener e um ouvinte
        //usuarioReferencia.addValueEventListener(new ValueEventListener() {
          //  @Override
            //metodo que sera chamado sempre que os dados no firebase for modificados
            //public void onDataChange(DataSnapshot dataSnapshot) {
              //  String value = null;
                //value = dataSnapshot.getValue().toString();

               //Log.i("FIREBASE INTEIRO", String.valueOf(value));

               // Log.i("FIREBASE", dataSnapshot.getValue().toString());

            //}

            //@Override
            //erro para caso ocorra um erro ao recuperar os dados do banco
            //public void onCancelled(DatabaseError databaseError) {

            //}
        //});
       // usuarioReferencia.child(idUserC).setValue("DADOS DO BANCO");

        /*
        Usuario usuario = new Usuario();
        usuario.setNome("Ana Helena");
        usuario.setSobrenome("Silva");
        usuario.setIdade(25);
        usuario.setSexo("Feminino");
        usuarioReferencia.child("003").setValue(usuario);
            */
       // firebaseReferencia.child("usuarios").setValue("kenya");
           // usuarioReferencia.child("002").child("nome").setValue("FuRy Fernandes");
        //Banco De Dados
        bancoDados = openOrCreateDatabase("notaRespostas", MODE_PRIVATE,null);
        bancoDados.execSQL("CREATE TABLE IF NOT EXISTS notas(id INTEGER PRIMARY KEY AUTOINCREMENT, respPergunta1 INTEGER(3)," +
                " respPergunta2 INTEGER(3),respPergunta3 INTEGER(3), respPergunta4 INTEGER(3), respPergunta5 INTEGER(3)," +
                "respPergunta6 INTEGER(3), respPergunta7 INTEGER(3) )");








        ////////////////////////////////////////

        //instanciando os botoes de voltar
        botaoEtapa1 = findViewById(R.id.botaoEtapa1);
        botaoEtapa2 = findViewById(R.id.botaoEtapa2);
        botaoEtapa3 = findViewById(R.id.botaoEtapa3);
        botaoEtapa4 = findViewById(R.id.botaoEtapa4);
        botaoEtapa5 = findViewById(R.id.botaoEtapa5);
        botaoEtapa6 = findViewById(R.id.botaoEtapa6);
        botaoEtapa7 = findViewById(R.id.botaoEtapa7);



        //instanciando os layouts
        lResultId = findViewById(R.id.layoutComplete);
        lPerguntasId = findViewById(R.id.layoutPerguntasId);
        lTermosId = findViewById(R.id.layoutTermosId);
        lSobreId = findViewById(R.id.layoutSobreId);
        lEquipeId = findViewById(R.id.layoutEquipeId);
        lTermosSubId = findViewById(R.id.layoutTermosSubId);
        lLoadingId = findViewById(R.id.layoutLoadingId);
        layoutComp = findViewById(R.id.layoutDados);
        edNome = findViewById(R.id.edNome);
        confNome = findViewById(R.id.buttonRes);


        //instanciando os layouts para inflar

        stasis = findViewById(R.id.emoInfla0);
        inEmoj1 = findViewById(R.id.emoInfla1);
        inEmoj2 = findViewById(R.id.emoInfla2);
        inEmoj3 = findViewById(R.id.emoInfla3);
        inEmoj4 = findViewById(R.id.emoInfla4);
        inEmoj5 = findViewById(R.id.emoInfla5);
        inEmoj6 = findViewById(R.id.emoInfla6);
        inEmoj7 = findViewById(R.id.emoInfla7);



        //----------------------------------------------
        // BOTOES PARA CONFIRMAR OS TERMOS
        checkTermos = findViewById(R.id.checkTermosId);

        scrollViewTermos = findViewById(R.id.scrollViewTermosId);

        botaoTermos = findViewById(R.id.botaoTermosId);


        //-----------------------------------------------

        opcoesSeek =findViewById(R.id.opcoesSeekId);
        textInteliResult = findViewById(R.id.textInteliResult);
        textInteliResult2 = findViewById(R.id.textInteliResult2);


        botao = findViewById(R.id.botaoC);
        pergunta = findViewById(R.id.perguntasId);
        //imagemIlust = findViewById(R.id.imagemId);






        //VERIFICADOR PARA PASSAR DOS TERMOS
        //E PARA QUANDO O APP FOR FECHADO E ABERTO NOVAMENTE





                ImageView avatar =  findViewById(R.id.perfilFotoId);



        botaoTermos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ( checkTermos.isChecked()==true ){
                    contador = 1;
                    estadoAtualEtapa=1;
                    //nameTextView.setText(nomeUserC);
                   // emailTextView.setText(emailUserC);
                    //idTextView.setText(idUserC);
                    atualizarPergunta();
                    }else {

                }

            }

        });







        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (corrigir == false) {
                    if (contador == 0) {
                        contador = 1;
                    }

                    if (notaPergunta7 == 0) {
                        contador = 7;
                    }
                    if (notaPergunta6 == 0) {
                        contador = 6;

                    }
                    if (notaPergunta5 == 0) {
                        contador = 5;
                    }
                    if (notaPergunta4 == 0) {
                        contador = 4;
                    }
                    if (notaPergunta3 == 0) {
                        contador = 3;
                    }
                    if (notaPergunta2 == 0) {
                        contador = 2;
                    }
                    if (notaPergunta1 == 0) {
                        contador = 1;

                    }
                    atualizarPergunta();

                    salvarResposta(contador);
                    limparResposta(contador);

                    atualizarPergunta();


                    verificarPerguntaEscolhida();
                }

                if (corrigir == true){
                    contador ++;

                    botao.setText("CONFIRMAR RESPOSTAS");
                    if (contador > 8 ){
                        recuperarTarefas();
                            if(suc == true) {
                                salvarTarefa(estadoAtualEtapa, notaPergunta1, notaPergunta2, notaPergunta3, notaPergunta4, notaPergunta5, notaPergunta6, notaPergunta7);
                                suc = false;
                                contafaz = true;
                            }
                                        if (passT == true){

                                            limparResposta(0);
                                            contador = 1;
                                            //estadoAtualEtapa++;
                                            recuperarTarefas();
                                            atualizarPergunta();


                                            contafaz = false;
                                            passT = false;}


                            }else
                    {
                        Toast.makeText(TelaInicial.this, "Não foi possivel salvar seus dados verifique sua conexão", Toast.LENGTH_LONG ).show();

                    }





                }



            }
        });


        //---------------------------------------------------------------->>>
       // recuperarTarefas();
        //salvarTarefa(1,1,2,3,4,5,6,7);

        //*******BOTOES DE ETAPA ATUAL*********//

        botaoEtapa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contador > 1 && contador <=7){


                    contador = 1;

                    atualizarPergunta();

                }
            }
        });

        //BOTAO 2 ***********

        botaoEtapa2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contador > 1 && contador <=7){

                    limparResposta(contador);
                    contador = 2;
                    atualizarPergunta();

                }
            }
        });

        botaoEtapa3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contador > 1 && contador <=7){
                    limparResposta(contador);
                    contador = 3;
                    atualizarPergunta();

                }
            }
        });

        botaoEtapa4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contador > 1 && contador <=7 ){
                    limparResposta(contador);
                    contador = 4;
                    atualizarPergunta();

                }
            }
        });


        botaoEtapa5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contador > 1 && contador <=7){
                    limparResposta(contador);
                    contador = 5;
                    atualizarPergunta();

                }
            }
        });

        botaoEtapa6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contador > 1 && contador<=7){
                    limparResposta(contador);
                    contador = 6;
                    atualizarPergunta();

                }
            }
        });

        botaoEtapa7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contador > 1 && contador <=7 ){
                    limparResposta(contador);
                    contador = 7;
                    atualizarPergunta();

                }
            }
        });

            //Seekbar
        opcoesSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress +=1;
                inflEmoj(progress);

                estadoAtualPergunta = progress;
                lkver = false;
                //verificarPerguntaEscolhida(estadoAtualPergunta);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            //mostrar a imagem da opção escolhida
                inflEmoj(0);
                if (lkver == true){
                    estadoAtualPergunta = 1;
                }
                //verificarPerguntaEscolhida(estadoAtualPergunta);

            }
        });
       //ImageView avatar =  findViewById(R.id.perfilFotoId);
       // Glide.with(this).load(link).into(avatar);



        //****FIM




    }

    private void setUserData(FirebaseUser user) {
        //nameTextView.setText(user.getDisplayName());
        nomeUserC = user.getDisplayName();
        emailUserC = user.getEmail();
        idUserC = user.getUid();
        link = user.getPhotoUrl();
    }
private void limparResposta(int numero){
        if (numero == 1){
            if (notaPergunta1 == notaPergunta2){
            notaPergunta2 = 0;
                botaoEtapa2.setVisibility(View.INVISIBLE);}
                if (notaPergunta1 == notaPergunta3){
                    notaPergunta3 = 0;
                    botaoEtapa3.setVisibility(View.INVISIBLE);
                }

                    if (notaPergunta1 == notaPergunta4){
                        notaPergunta4 = 0;
                        botaoEtapa4.setVisibility(View.INVISIBLE);}
                        if (notaPergunta1 == notaPergunta5){
                            notaPergunta5 = 0;
                            botaoEtapa5.setVisibility(View.INVISIBLE);
            }
                                if (notaPergunta1 == notaPergunta6){
                                    notaPergunta6 = 0;
                                     botaoEtapa6.setVisibility(View.INVISIBLE);}
                                        if (notaPergunta1 == notaPergunta7){
                                            notaPergunta7 = 0;
                                            botaoEtapa7.setVisibility(View.INVISIBLE);
            }


        }else if (numero == 2){
            if (notaPergunta2 == notaPergunta1){
            notaPergunta1 = 0;
            botaoEtapa1.setVisibility(View.INVISIBLE);}
            if (notaPergunta2 == notaPergunta3){
                notaPergunta3 = 0;
                botaoEtapa3.setVisibility(View.INVISIBLE);
            }

            if (notaPergunta2 == notaPergunta4){
                notaPergunta4 = 0;
                botaoEtapa4.setVisibility(View.INVISIBLE);}
            if (notaPergunta2 == notaPergunta5){
                notaPergunta5 = 0;
                botaoEtapa5.setVisibility(View.INVISIBLE);
            }
            if (notaPergunta2 == notaPergunta6){
                notaPergunta6 = 0;
                botaoEtapa6.setVisibility(View.INVISIBLE);}
            if (notaPergunta2 == notaPergunta7){
                notaPergunta7 = 0;
                botaoEtapa2.setVisibility(View.INVISIBLE);
            }
        }
        else if (numero == 3){
            if (notaPergunta3 == notaPergunta2){
            notaPergunta2 = 0;
            botaoEtapa2.setVisibility(View.INVISIBLE);}
            if (notaPergunta3 == notaPergunta1){
                notaPergunta1 = 0;
                botaoEtapa1.setVisibility(View.INVISIBLE);
            }

            if (notaPergunta3 == notaPergunta4){
                notaPergunta4 = 0;
                botaoEtapa4.setVisibility(View.INVISIBLE);}
            if (notaPergunta3 == notaPergunta5){
                notaPergunta5 = 0;
                botaoEtapa5.setVisibility(View.INVISIBLE);
            }
            if (notaPergunta3 == notaPergunta6){
                notaPergunta6 = 0;
                botaoEtapa6.setVisibility(View.INVISIBLE);}
            if (notaPergunta3 == notaPergunta7){
                notaPergunta7 = 0;
                botaoEtapa7.setVisibility(View.INVISIBLE);
            }
        }

        else if (numero == 4){
            if (notaPergunta4 == notaPergunta2){
                notaPergunta2 = 0;
                botaoEtapa2.setVisibility(View.INVISIBLE);}
            if (notaPergunta4 == notaPergunta1){
                notaPergunta1 = 0;
                botaoEtapa1.setVisibility(View.INVISIBLE);
            }

            if (notaPergunta4 == notaPergunta3){
                notaPergunta3 = 0;
                botaoEtapa3.setVisibility(View.INVISIBLE);}
            if (notaPergunta4 == notaPergunta5){
                notaPergunta5 = 0;
                botaoEtapa5.setVisibility(View.INVISIBLE);
            }
            if (notaPergunta4 == notaPergunta6){
                notaPergunta6 = 0;
                botaoEtapa6.setVisibility(View.INVISIBLE);}
            if (notaPergunta4 == notaPergunta7){
                notaPergunta7 = 0;
                botaoEtapa7.setVisibility(View.INVISIBLE);
            }
        }else if (numero == 5){
            if (notaPergunta5 == notaPergunta2){
                notaPergunta2 = 0;
                botaoEtapa2.setVisibility(View.INVISIBLE);}
            if (notaPergunta5 == notaPergunta1){
                notaPergunta1 = 0;
                botaoEtapa1.setVisibility(View.INVISIBLE);
            }

            if (notaPergunta5 == notaPergunta4){
                notaPergunta4 = 0;
                botaoEtapa4.setVisibility(View.INVISIBLE);}
            if (notaPergunta5 == notaPergunta3){
                notaPergunta3 = 0;
                botaoEtapa3.setVisibility(View.INVISIBLE);
            }
            if (notaPergunta5 == notaPergunta6){
                notaPergunta6 = 0;
                botaoEtapa6.setVisibility(View.INVISIBLE);}
            if (notaPergunta7 == notaPergunta7){
                notaPergunta7 = 0;
                botaoEtapa7.setVisibility(View.INVISIBLE);
            }
        }else if (numero == 6){
            if (notaPergunta6 == notaPergunta2){
                notaPergunta2 = 0;
                botaoEtapa2.setVisibility(View.INVISIBLE);}
            if (notaPergunta6 == notaPergunta1){
                notaPergunta1 = 0;
                botaoEtapa1.setVisibility(View.INVISIBLE);
            }

            if (notaPergunta6 == notaPergunta4){
                notaPergunta4 = 0;
                botaoEtapa4.setVisibility(View.INVISIBLE);}
            if (notaPergunta6 == notaPergunta5){
                notaPergunta5 = 0;
                botaoEtapa5.setVisibility(View.INVISIBLE);
            }
            if (notaPergunta6 == notaPergunta3){
                notaPergunta3 = 0;
                botaoEtapa3.setVisibility(View.INVISIBLE);}
            if (notaPergunta6 == notaPergunta7){
                notaPergunta7 = 0;
                botaoEtapa7.setVisibility(View.INVISIBLE);
            }
        }else if (numero == 7){
            if (notaPergunta7 == notaPergunta2){
                notaPergunta2 = 0;
                botaoEtapa2.setVisibility(View.INVISIBLE);}
            if (notaPergunta7 == notaPergunta1){
                notaPergunta1 = 0;
                botaoEtapa1.setVisibility(View.INVISIBLE);
            }

            if (notaPergunta7 == notaPergunta4){
                notaPergunta4 = 0;
                botaoEtapa4.setVisibility(View.INVISIBLE);}
            if (notaPergunta7 == notaPergunta5){
                notaPergunta5 = 0;
                botaoEtapa5.setVisibility(View.INVISIBLE);
            }
            if (notaPergunta7 == notaPergunta6){
                notaPergunta6 = 0;
                botaoEtapa6.setVisibility(View.INVISIBLE);}
            if (notaPergunta7 == notaPergunta3){
                notaPergunta3 = 0;
                botaoEtapa3.setVisibility(View.INVISIBLE);
            }
        }else if (numero == 0){

            botaoEtapa1.setVisibility(View.INVISIBLE);
            botaoEtapa2.setVisibility(View.INVISIBLE);
            botaoEtapa3.setVisibility(View.INVISIBLE);
            botaoEtapa4.setVisibility(View.INVISIBLE);
            botaoEtapa5.setVisibility(View.INVISIBLE);
            botaoEtapa6.setVisibility(View.INVISIBLE);
            botaoEtapa7.setVisibility(View.INVISIBLE);

            notaPergunta1 = 0;
            notaPergunta2 = 0;
            notaPergunta3 = 0;
            notaPergunta4 = 0;
            notaPergunta5 = 0;
            notaPergunta6 = 0;
            notaPergunta7 = 0;

        }
        botao.setText("PROXIMO");

}
private void setaremoji(){
        if (contador == 1){
        }

}

    private void salvarResposta(int contador) {
        //salvar respostas em variaveis locais
        if (contador == 1){
            if (estadoAtualPergunta == 0){
                estadoAtualPergunta = 1;
            }


            notaPergunta1 = estadoAtualPergunta;

            if (notaPergunta1 == 1){
                botaoEtapa1.setVisibility(View.VISIBLE);
                botaoEtapa1.setImageResource(R.drawable.emoji1);
            }else if (notaPergunta1 == 2){
                botaoEtapa1.setVisibility(View.VISIBLE);
                botaoEtapa1.setImageResource(R.drawable.emoji2);
            }else if (notaPergunta1 == 3){
                botaoEtapa1.setVisibility(View.VISIBLE);
                botaoEtapa1.setImageResource(R.drawable.emoji3);
            }else if (notaPergunta1 == 4){
                botaoEtapa1.setVisibility(View.VISIBLE);
                botaoEtapa1.setImageResource(R.drawable.emoji4);
            }else if (notaPergunta1 == 5){
                botaoEtapa1.setVisibility(View.VISIBLE);
                botaoEtapa1.setImageResource(R.drawable.emoji5);
            }else if (notaPergunta1 == 6){
                botaoEtapa1.setVisibility(View.VISIBLE);
                botaoEtapa1.setImageResource(R.drawable.emoji6);
            }else if (notaPergunta1 == 7){
                botaoEtapa1.setVisibility(View.VISIBLE);
                botaoEtapa1.setImageResource(R.drawable.emoji7);
            }

        }else if(contador == 2){
            botaoEtapa2.setVisibility(View.VISIBLE);
            notaPergunta2 = estadoAtualPergunta;
            if (notaPergunta2 == 1){
                botaoEtapa2.setVisibility(View.VISIBLE);
                botaoEtapa2.setImageResource(R.drawable.emoji1);
            }else if (notaPergunta2 == 2){
                botaoEtapa2.setVisibility(View.VISIBLE);
                botaoEtapa2.setImageResource(R.drawable.emoji2);
            }else if (notaPergunta2 == 3){
                botaoEtapa2.setVisibility(View.VISIBLE);
                botaoEtapa2.setImageResource(R.drawable.emoji3);
            }else if (notaPergunta2 == 4){
                botaoEtapa2.setVisibility(View.VISIBLE);
                botaoEtapa2.setImageResource(R.drawable.emoji4);
            }else if (notaPergunta2 == 5){
                botaoEtapa2.setVisibility(View.VISIBLE);
                botaoEtapa2.setImageResource(R.drawable.emoji5);
            }else if (notaPergunta2 == 6){
                botaoEtapa2.setVisibility(View.VISIBLE);
                botaoEtapa2.setImageResource(R.drawable.emoji6);
            }else if (notaPergunta2 == 7){
                botaoEtapa2.setVisibility(View.VISIBLE);
                botaoEtapa2.setImageResource(R.drawable.emoji7);
            }
            }
            else
                if(contador == 3){
            notaPergunta3 = estadoAtualPergunta;
                    if (notaPergunta3 == 1){
                        botaoEtapa3.setVisibility(View.VISIBLE);
                        botaoEtapa3.setImageResource(R.drawable.emoji1);
                    }else if (notaPergunta3 == 2){
                        botaoEtapa3.setVisibility(View.VISIBLE);
                        botaoEtapa3.setImageResource(R.drawable.emoji2);
                    }else if (notaPergunta3 == 3){
                        botaoEtapa3.setVisibility(View.VISIBLE);
                        botaoEtapa3.setImageResource(R.drawable.emoji3);
                    }else if (notaPergunta3 == 4){
                        botaoEtapa3.setVisibility(View.VISIBLE);
                        botaoEtapa3.setImageResource(R.drawable.emoji4);
                    }else if (notaPergunta3 == 5){
                        botaoEtapa3.setVisibility(View.VISIBLE);
                        botaoEtapa3.setImageResource(R.drawable.emoji5);
                    }else if (notaPergunta3 == 6){
                        botaoEtapa3.setVisibility(View.VISIBLE);
                        botaoEtapa3.setImageResource(R.drawable.emoji6);
                    }else if (notaPergunta3 == 7){
                        botaoEtapa3.setVisibility(View.VISIBLE);
                        botaoEtapa3.setImageResource(R.drawable.emoji7);
                    }
            }
            else
                if(contador == 4){
                    botaoEtapa4.setVisibility(View.VISIBLE);
            notaPergunta4 = estadoAtualPergunta;
                    if (notaPergunta4 == 1){
                        botaoEtapa4.setVisibility(View.VISIBLE);
                        botaoEtapa4.setImageResource(R.drawable.emoji1);
                    }else if (notaPergunta4 == 2){
                        botaoEtapa4.setVisibility(View.VISIBLE);
                        botaoEtapa4.setImageResource(R.drawable.emoji2);
                    }else if (notaPergunta4 == 3){
                        botaoEtapa4.setVisibility(View.VISIBLE);
                        botaoEtapa4.setImageResource(R.drawable.emoji3);
                    }else if (notaPergunta4 == 4){
                        botaoEtapa4.setVisibility(View.VISIBLE);
                        botaoEtapa4.setImageResource(R.drawable.emoji4);
                    }else if (notaPergunta4 == 5){
                        botaoEtapa4.setVisibility(View.VISIBLE);
                        botaoEtapa4.setImageResource(R.drawable.emoji5);
                    }else if (notaPergunta4 == 6){
                        botaoEtapa4.setVisibility(View.VISIBLE);
                        botaoEtapa4.setImageResource(R.drawable.emoji6);
                    }else if (notaPergunta4 == 7){
                        botaoEtapa4.setVisibility(View.VISIBLE);
                        botaoEtapa4.setImageResource(R.drawable.emoji7);
                    }
            }
            else
                if(contador == 5){
                    botaoEtapa5.setVisibility(View.VISIBLE);
            notaPergunta5 = estadoAtualPergunta;
                    if (notaPergunta5 == 1){
                        botaoEtapa5.setVisibility(View.VISIBLE);
                        botaoEtapa5.setImageResource(R.drawable.emoji1);
                    }else if (notaPergunta5 == 2){
                        botaoEtapa5.setVisibility(View.VISIBLE);
                        botaoEtapa5.setImageResource(R.drawable.emoji2);
                    }else if (notaPergunta5 == 3){
                        botaoEtapa5.setVisibility(View.VISIBLE);
                        botaoEtapa5.setImageResource(R.drawable.emoji3);
                    }else if (notaPergunta5 == 4){
                        botaoEtapa5.setVisibility(View.VISIBLE);
                        botaoEtapa5.setImageResource(R.drawable.emoji4);
                    }else if (notaPergunta5 == 5){
                        botaoEtapa5.setVisibility(View.VISIBLE);
                        botaoEtapa5.setImageResource(R.drawable.emoji5);
                    }else if (notaPergunta5 == 6){
                        botaoEtapa5.setVisibility(View.VISIBLE);
                        botaoEtapa5.setImageResource(R.drawable.emoji6);
                    }else if (notaPergunta5 == 7){
                        botaoEtapa5.setVisibility(View.VISIBLE);
                        botaoEtapa5.setImageResource(R.drawable.emoji7);
                    }
            }
            else
                if(contador == 6){
                    botaoEtapa6.setVisibility(View.VISIBLE);
            notaPergunta6 = estadoAtualPergunta;
                    if (notaPergunta6 == 1){
                        botaoEtapa6.setVisibility(View.VISIBLE);
                        botaoEtapa6.setImageResource(R.drawable.emoji1);
                    }else if (notaPergunta6 == 2){
                        botaoEtapa6.setVisibility(View.VISIBLE);
                        botaoEtapa6.setImageResource(R.drawable.emoji2);
                    }else if (notaPergunta6 == 3){
                        botaoEtapa6.setVisibility(View.VISIBLE);
                        botaoEtapa6.setImageResource(R.drawable.emoji3);
                    }else if (notaPergunta6 == 4){
                        botaoEtapa6.setVisibility(View.VISIBLE);
                        botaoEtapa6.setImageResource(R.drawable.emoji4);
                    }else if (notaPergunta6 == 5){
                        botaoEtapa6.setVisibility(View.VISIBLE);
                        botaoEtapa6.setImageResource(R.drawable.emoji5);
                    }else if (notaPergunta6 == 6){
                        botaoEtapa6.setVisibility(View.VISIBLE);
                        botaoEtapa6.setImageResource(R.drawable.emoji6);
                    }else if (notaPergunta6 == 7){
                        botaoEtapa6.setVisibility(View.VISIBLE);
                        botaoEtapa6.setImageResource(R.drawable.emoji7);
                    }
        }
        else
            if(contador == 7){
            notaPergunta7 = estadoAtualPergunta;
                if (notaPergunta7 == 1){
                    botaoEtapa7.setVisibility(View.VISIBLE);
                    botaoEtapa7.setImageResource(R.drawable.emoji1);
                }else if (notaPergunta7 == 2){
                    botaoEtapa7.setVisibility(View.VISIBLE);
                    botaoEtapa7.setImageResource(R.drawable.emoji2);
                }else if (notaPergunta7 == 3){
                    botaoEtapa7.setVisibility(View.VISIBLE);
                    botaoEtapa7.setImageResource(R.drawable.emoji3);
                }else if (notaPergunta7 == 4){
                    botaoEtapa7.setVisibility(View.VISIBLE);
                    botaoEtapa7.setImageResource(R.drawable.emoji4);
                }else if (notaPergunta7 == 5){
                    botaoEtapa7.setVisibility(View.VISIBLE);
                    botaoEtapa7.setImageResource(R.drawable.emoji5);
                }else if (notaPergunta7 == 6){
                    botaoEtapa7.setVisibility(View.VISIBLE);
                    botaoEtapa7.setImageResource(R.drawable.emoji6);
                }else if (notaPergunta7 == 7){
                    botaoEtapa7.setVisibility(View.VISIBLE);
                    botaoEtapa7.setImageResource(R.drawable.emoji7);
                }

        }
    }

    private void verificarPerguntaEscolhida() {

        if (notaPergunta1 != notaPergunta2 && notaPergunta2 != notaPergunta3 && notaPergunta3 != notaPergunta4 && notaPergunta4 != notaPergunta5 && notaPergunta5 !=notaPergunta6 && notaPergunta6 != notaPergunta7){
            if (notaPergunta1 != 0 && notaPergunta2 != 0 && notaPergunta3 != 0 && notaPergunta4 != 0 && notaPergunta5 != 0 && notaPergunta6 != 0 && notaPergunta7 != 0 )
                corrigir = true;
        }




    }


    private void perguntasE1 (){
        try{
            //colocar mais um verificador em cima para ver se a pergunta foi respondida

            // etapa atual mudar o tema
            etapaAtual.setText(R.string.tituloEtapa1);


            //************** PERGUNTA 1 ETAPA 1 ***********
            if (contador == 1) {
                ocultarLayout(6);
                pergunta.setText(R.string.pergunta1Etapa1);


                //Glide.with(getApplicationContext()).load(link).into(fotoPerfil);
               // Glide.with(this).load(link).into(imagemIlust);

                imagemIlust.setImageResource(R.drawable.game_over);
                if (passed == false){
                    TapTargetView.showFor(this,                 // `this` is an Activity
                            TapTarget.forView(findViewById(R.id.etapaAtualId), "Etapa", "Aqui é a pergunta da etapa atual, fique atento a ela para entender a pergunta logo abaixo")
                                    .tintTarget(false)
                                    .outerCircleColor(R.color.azulgrad)
                                    .titleTextColor(R.color.white)
                                    .titleTextSize(24)
                                    .descriptionTextColor(R.color.black)
                                    .textTypeface(Typeface.SANS_SERIF)
                                    .dimColor(R.color.black)
                                    .drawShadow(true)
                                    .cancelable(false)
                                    .transparentTarget(false)
                                    .targetRadius(110),

                            new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                                @Override
                                public void onTargetClick(TapTargetView view) {
                                    super.onTargetClick(view);      // This call is optional
                                    chamaProx2();
                                }
                            }

                    );
                    passed = true;
                }
                }

            //************** PERGUNTA 2 ETAPA 1 ***********
            if (contador == 2) {
                pergunta.setText(R.string.pergunta2Etapa1);
                ocultarLayout(6);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 3 ETAPA 1 ***********
            if (contador == 3) {
                pergunta.setText(R.string.pergunta3Etapa1);
                ocultarLayout(6);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 4 ETAPA 1 ***********
            if (contador == 4) {
                pergunta.setText(R.string.pergunta4Etapa1);
                ocultarLayout(6);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 5 ETAPA 1 ***********
            if (contador == 5) {
                pergunta.setText(R.string.pergunta5Etapa1);
                ocultarLayout(6);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 6 ETAPA 1 ***********
            if (contador == 6) {
                pergunta.setText(R.string.pergunta6Etapa1);
                ocultarLayout(6);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 7 ETAPA 1 ***********
            if (contador == 7) {
                pergunta.setText(R.string.pergunta7Etapa1);
                ocultarLayout(6);
                imagemIlust.setImageResource(R.drawable.game_over);}
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }



    //*************************************************//

    private void perguntasE2 (){
        try{
            //colocar mais um verificador em cima para ver se a pergunta foi respondida
            etapaAtual.setText(R.string.tituloEtapa2);

            //************** PERGUNTA 1 ETAPA 2 ***********
            if (contador == 1) {
                pergunta.setText(R.string.pergunta1Etapa2);
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 2 ETAPA 2 ***********
            if (contador == 2) {
                pergunta.setText(R.string.pergunta2Etapa2);
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 3 ETAPA 2 ***********
            if (contador == 3) {
                pergunta.setText(R.string.pergunta3Etapa2);
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 4 ETAPA 2 ***********
            if (contador == 4) {
                pergunta.setText(R.string.pergunta4Etapa2);
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 5 ETAPA 2 ***********
            if (contador == 5) {
                pergunta.setText(R.string.pergunta5Etapa2);
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 6 ETAPA 2 ***********
            if (contador == 6) {
                pergunta.setText(R.string.pergunta6Etapa2);
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 7 ETAPA 2 ***********
            if (contador == 7) {
                pergunta.setText(R.string.pergunta7Etapa2);
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over); }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }



    //*************************************************//

    private void perguntasE3 (){
        try{
            //colocar mais um verificador em cima para ver se a pergunta foi respondida
            etapaAtual.setText("Quando Estou No Trabalho Ou Na Escola Prefiro?");

            //************** PERGUNTA 1 ETAPA 3 ***********
            if (contador == 1) {
                pergunta.setText("Levantar E Andar Periodicamente");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 2 ETAPA 3 ***********
            if (contador == 2) {
                pergunta.setText("Fazer Algo Funcionar");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 3 ETAPA 3 ***********
            if (contador == 3) {
                pergunta.setText("Trabalhar Com Pessoas");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 4 ETAPA 3 ***********
            if (contador == 4) {
                pergunta.setText("Trabalhar Sozinho");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 5 ETAPA 3 ***********
            if (contador == 5) {
                pergunta.setText("Conversar Sobre Ideias");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 6 ETAPA 3 ***********
            if (contador == 6) {
                pergunta.setText("Analisar Dados");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 7 ETAPA 3 ***********
            if (contador == 7) {
                pergunta.setText("Identificar Padroes Sonoros Em Equipamentos");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    //*************************************************//

    private void perguntasE4 (){
        try{
            //colocar mais um verificador em cima para ver se a pergunta foi respondida
            etapaAtual.setText("O Tipo De Pergunta Que Mais Faço É?");

            //************** PERGUNTA 1 ETAPA 4 ***********
            if (contador == 1) {
                pergunta.setText("Onde?");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 2 ETAPA 4 ***********
            if (contador == 2) {
                pergunta.setText("Como?");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 3 ETAPA 4 ***********
            if (contador == 3) {
                pergunta.setText("Quem?");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 4 ETAPA 4 ***********
            if (contador == 4) {
                pergunta.setText("Para Que?");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 5 ETAPA 4 ***********
            if (contador == 5) {
                pergunta.setText("Por Que?");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 6 ETAPA 4 ***********
            if (contador == 6) {
                pergunta.setText("O Que?");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 7 ETAPA 4 ***********
            if (contador == 7) {
                pergunta.setText("Quando?");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    //*************************************************//

    private void perguntasE5 (){
        try{
            //colocar mais um verificador em cima para ver se a pergunta foi respondida
            etapaAtual.setText("No Tempo Livre Gosto Mais De:");

            //************** PERGUNTA 1 ETAPA 5 ***********
            if (contador == 1) {
                pergunta.setText("Levantar E Andar Periodicamente");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 2 ETAPA 5 ***********
            if (contador == 2) {
                pergunta.setText("Fazer Algo Funcionar");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 3 ETAPA 5 ***********
            if (contador == 3) {
                pergunta.setText("Trabalhar Com Pessoas");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 4 ETAPA 5 ***********
            if (contador == 4) {
                pergunta.setText("Trabalhar Sozinho");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 5 ETAPA 5 ***********
            if (contador == 5) {
                pergunta.setText("Conversar Sobre Ideias");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 6 ETAPA 5 ***********
            if (contador == 6) {
                pergunta.setText("Analisar Dados");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 7 ETAPA 5 ***********
            if (contador == 7) {
                pergunta.setText("Identificar Padroes Sonoros Em Equipamentos");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    //*************************************************//

    private void perguntasE6 (){
        try{
            //colocar mais um verificador em cima para ver se a pergunta foi respondida
            etapaAtual.setText("Tenho Facilidade Em:");

            //************** PERGUNTA 1 ETAPA 6 ***********
            if (contador == 1) {
                pergunta.setText("Adquirir habilidades pela pratica");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 2 ETAPA 6 ***********
            if (contador == 2) {
                pergunta.setText("Analisar e Descobrir Formas e Detalhes");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 3 ETAPA 6 ***********
            if (contador == 3) {
                pergunta.setText("Ouvir e compartilhar ideias");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 4 ETAPA 6 ***********
            if (contador == 4) {
                pergunta.setText("Elaborar Teorias:");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 5 ETAPA 6 ***********
            if (contador == 5) {
                pergunta.setText("Discutir Informações");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 6 ETAPA 6 ***********
            if (contador == 6) {
                pergunta.setText("Obter e Classificar Informações");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 7 ETAPA 6 ***********
            if (contador == 7) {
                pergunta.setText("Ler Ouvindo Música");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    //*************************************************//

    private void perguntasE7 (){
        try{
            //colocar mais um verificador em cima para ver se a pergunta foi respondida
            etapaAtual.setText("Em Minha Casa");

            //************** PERGUNTA 1 ETAPA 7 ***********
            if (contador == 1) {
                pergunta.setText("Não Fico Parado/A:");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 2 ETAPA 7 ***********
            if (contador == 2) {
                pergunta.setText("Conserto Coisas");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 3 ETAPA 7 ***********
            if (contador == 3) {
                pergunta.setText("Ajudo Outros Nas Tarefas");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 4 ETAPA 7 ***********
            if (contador == 4) {
                pergunta.setText("Fico Em Meu Canto");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 5 ETAPA 7 ***********
            if (contador == 5) {
                pergunta.setText("Falo Sobre Meu Dia");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 6 ETAPA 7 ***********
            if (contador == 6) {
                pergunta.setText("Organizo Cada Detalhe");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 7 ETAPA 7 ***********
            if (contador == 7) {
                pergunta.setText("Sempre Escuto Musica");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    //*************************************************//

    private void perguntasE8 (){
        try{
            //colocar mais um verificador em cima para ver se a pergunta foi respondida
            etapaAtual.setText("As Pessoas Podem Me Definir Por Esta Palavra");

            //************** PERGUNTA 1 ETAPA 8 ***********
            if (contador == 1) {
                pergunta.setText("Esportista");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 2 ETAPA 8 ***********
            if (contador == 2) {
                pergunta.setText("Competente");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 3 ETAPA 8 ***********
            if (contador == 3) {
                pergunta.setText("Perceptivo");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 4 ETAPA 8 ***********
            if (contador == 4) {
                pergunta.setText("Analitico");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 5 ETAPA 8 ***********
            if (contador == 5) {
                pergunta.setText("Teórico");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 6 ETAPA 8 ***********
            if (contador == 6) {
                pergunta.setText("Lógico");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 7 ETAPA 8 ***********
            if (contador == 7) {
                pergunta.setText("Artista");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    //*************************************************//

    private void perguntasE9 (){
        try{
            //colocar mais um verificador em cima para ver se a pergunta foi respondida
            etapaAtual.setText("Gosto Mais De Aprender Atraves De");

            //************** PERGUNTA 1 ETAPA 9 ***********
            if (contador == 1) {
                pergunta.setText("Demonstrações E Experiencias");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 2 ETAPA 9 ***********
            if (contador == 2) {
                pergunta.setText("Atividades Estruturadas Passo a Passo");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 3 ETAPA 9 ***********
            if (contador == 3) {
                pergunta.setText("Discussao De Casos Voltados Para Pessoas");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 4 ETAPA 9 ***********
            if (contador == 4) {
                pergunta.setText("Leitura De Livros-Textos");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 5 ETAPA 9 ***********
            if (contador == 5) {
                pergunta.setText("Palestras Formais");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 6 ETAPA 9 ***********
            if (contador == 6) {
                pergunta.setText("Exercicios De Analise De Fatos, Dados e Números");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 7 ETAPA 9 ***********
            if (contador == 7) {
                pergunta.setText("Historias e músicas");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    //*************************************************//

    private void perguntasE10 (){
        try{
            //colocar mais um verificador em cima para ver se a pergunta foi respondida
            etapaAtual.setText("Eu Me Considero");

            //************** PERGUNTA 1 ETAPA 10 ***********
            if (contador == 1) {
                pergunta.setText("Agil");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 2 ETAPA 10 ***********
            if (contador == 2) {
                pergunta.setText("Detalhista");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 3 ETAPA 10 ***********
            if (contador == 3) {
                pergunta.setText("Amigo");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 4 ETAPA 10 ***********
            if (contador == 4) {
                pergunta.setText("Sensivel");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 5 ETAPA 10 ***********
            if (contador == 5) {
                pergunta.setText("Comunicativo");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 6 ETAPA 10 ***********
            if (contador == 6) {
                pergunta.setText("Racional");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}

            //************** PERGUNTA 7 ETAPA 10 ***********
            if (contador == 7) {
                pergunta.setText("Musical");
                //imagemIlust.setVisibility(View.INVISIBLE);
                imagemIlust.setImageResource(R.drawable.game_over);}
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_teste) {
            ocultarLayout(7);
            usuarioReferencia.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(idUserC).child("TOTAL").exists()){
                        ocultarLayout(1);

                    }else if (dataSnapshot.child(idUserC).child("ETAPA 1").exists())
                    {ocultarLayout(6);}else{
                        ocultarLayout(2);
                    }

                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        } else if (id == R.id.nav_aplicativo) {
            ocultarLayout(3);

        } else if (id == R.id.nav_desenvolvimento) {
            ocultarLayout(4);

        } else if (id == R.id.nav_termos) {
            ocultarLayout(5);

        } else if (id == R.id.nav_view) {

        }




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void ocultarLayout(int lay){
        if (lay ==1) {
            lPerguntasId.setVisibility(View.GONE);
            lTermosSubId.setVisibility(View.GONE);
            lEquipeId.setVisibility(View.GONE);
            lSobreId.setVisibility(View.GONE);
            lTermosId.setVisibility(View.GONE);
            lResultId.setVisibility(View.VISIBLE);
            lLoadingId.setVisibility(View.GONE);
            layoutComp.setVisibility(View.GONE);
                    }else if (lay ==2) {
            lPerguntasId.setVisibility(View.GONE);
            lTermosSubId.setVisibility(View.GONE);
            lEquipeId.setVisibility(View.GONE);
            lSobreId.setVisibility(View.GONE);
            lTermosId.setVisibility(View.VISIBLE);
            lResultId.setVisibility(View.GONE);
            lLoadingId.setVisibility(View.GONE);
            layoutComp.setVisibility(View.GONE);
                if (exibido == true){
            TapTargetView.showFor(this,                 // `this` is an Activity
                    TapTarget.forView(findViewById(R.id.checkTermosId), "Confirmação", "Apos ler os termos marque esta caixa para confirmar")
                            .tintTarget(false)
                            .outerCircleColor(R.color.azulgrad)
                            .titleTextColor(R.color.white)
                            .titleTextSize(24)
                            .descriptionTextColor(R.color.black)
                            .textTypeface(Typeface.SANS_SERIF)
                            .dimColor(R.color.black)
                            .drawShadow(true)
                            .cancelable(false)
                            .transparentTarget(false)
                            .targetRadius(90),

                    new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                        @Override
                        public void onTargetClick(TapTargetView view) {
                            super.onTargetClick(view);      // This call is optional
                            chamaProx();
                        }
                    }

                    );
                exibido = false;}


        }else if (lay ==3) {
            lPerguntasId.setVisibility(View.GONE);
            lTermosSubId.setVisibility(View.GONE);
            lEquipeId.setVisibility(View.GONE);
            lSobreId.setVisibility(View.VISIBLE);
            lTermosId.setVisibility(View.GONE);
            lResultId.setVisibility(View.GONE);
            lLoadingId.setVisibility(View.GONE);
            layoutComp.setVisibility(View.GONE);
        }else if (lay ==4) {
            lPerguntasId.setVisibility(View.GONE);
            lTermosSubId.setVisibility(View.GONE);
            lEquipeId.setVisibility(View.VISIBLE);
            lSobreId.setVisibility(View.GONE);
            lTermosId.setVisibility(View.GONE);
            lResultId.setVisibility(View.GONE);
            lLoadingId.setVisibility(View.GONE);
            layoutComp.setVisibility(View.GONE);
        }else if (lay ==5) {
            lPerguntasId.setVisibility(View.GONE);
            lTermosSubId.setVisibility(View.VISIBLE);
            lEquipeId.setVisibility(View.GONE);
            lSobreId.setVisibility(View.GONE);
            lTermosId.setVisibility(View.GONE);
            lResultId.setVisibility(View.GONE);
            lLoadingId.setVisibility(View.GONE);
            layoutComp.setVisibility(View.GONE);}
        else if (lay ==6) {
            lPerguntasId.setVisibility(View.VISIBLE);
            lTermosSubId.setVisibility(View.GONE);
            lEquipeId.setVisibility(View.GONE);
            lSobreId.setVisibility(View.GONE);
            lTermosId.setVisibility(View.GONE);
            lResultId.setVisibility(View.GONE);
            lLoadingId.setVisibility(View.GONE);
            layoutComp.setVisibility(View.GONE);}

        else if (lay ==7) {
            layoutComp.setVisibility(View.GONE);
            lPerguntasId.setVisibility(View.GONE);
            lTermosSubId.setVisibility(View.GONE);
            lEquipeId.setVisibility(View.GONE);
            lSobreId.setVisibility(View.GONE);
            lTermosId.setVisibility(View.GONE);
            lResultId.setVisibility(View.GONE);
        lLoadingId.setVisibility(View.VISIBLE);}

        else if (lay == 8){
            layoutComp.setVisibility(View.VISIBLE);

            lPerguntasId.setVisibility(View.GONE);
            lTermosSubId.setVisibility(View.GONE);
            lEquipeId.setVisibility(View.GONE);
            lSobreId.setVisibility(View.GONE);
            lTermosId.setVisibility(View.GONE);
            lResultId.setVisibility(View.GONE);
            lLoadingId.setVisibility(View.GONE);
        }
}

    private void chamaProx() {

        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(findViewById(R.id.botaoTermosId), "Iniciando Teste", "Clique aqui confirmar que entendeu o teste e iniciar")
                        .tintTarget(false)
                        .outerCircleColor(R.color.azulgrad)
                        .titleTextColor(R.color.white)
                    .titleTextSize(24)
                    .descriptionTextColor(R.color.black)
                    .textTypeface(Typeface.SANS_SERIF)
                    .dimColor(R.color.black)
                    .drawShadow(true)
                        .targetRadius(70)
                        .cancelable(false)
                    .transparentTarget(false));

        }


        ////////////////////////////BANCO SQLITE ////////////////////////////////////////

        private void salvarTarefa(int etapa ,int valor1, int valor2 , int valor3 , int valor4 , int valor5, int valor6,int valor7){
            ocultarLayout(7);
            if (etapa == 1){
                usuarioReferencia.child(idUserC).child("ETAPA 1").child("RESPOSTA1").setValue(valor1);
                usuarioReferencia.child(idUserC).child("ETAPA 1").child("RESPOSTA2").setValue(valor2);
                usuarioReferencia.child(idUserC).child("ETAPA 1").child("RESPOSTA3").setValue(valor3);
                usuarioReferencia.child(idUserC).child("ETAPA 1").child("RESPOSTA4").setValue(valor4);
                usuarioReferencia.child(idUserC).child("ETAPA 1").child("RESPOSTA5").setValue(valor5);
                usuarioReferencia.child(idUserC).child("ETAPA 1").child("RESPOSTA6").setValue(valor6);
                usuarioReferencia.child(idUserC).child("ETAPA 1").child("RESPOSTA7").setValue(valor7);

            }if (etapa == 2){
                usuarioReferencia.child(idUserC).child("ETAPA 2").child("RESPOSTA1").setValue(valor1);
                usuarioReferencia.child(idUserC).child("ETAPA 2").child("RESPOSTA2").setValue(valor2);
                usuarioReferencia.child(idUserC).child("ETAPA 2").child("RESPOSTA3").setValue(valor3);
                usuarioReferencia.child(idUserC).child("ETAPA 2").child("RESPOSTA4").setValue(valor4);
                usuarioReferencia.child(idUserC).child("ETAPA 2").child("RESPOSTA5").setValue(valor5);
                usuarioReferencia.child(idUserC).child("ETAPA 2").child("RESPOSTA6").setValue(valor6);
                usuarioReferencia.child(idUserC).child("ETAPA 2").child("RESPOSTA7").setValue(valor7);

            }if (etapa == 3){
                usuarioReferencia.child(idUserC).child("ETAPA 3").child("RESPOSTA1").setValue(valor1);
                usuarioReferencia.child(idUserC).child("ETAPA 3").child("RESPOSTA2").setValue(valor2);
                usuarioReferencia.child(idUserC).child("ETAPA 3").child("RESPOSTA3").setValue(valor3);
                usuarioReferencia.child(idUserC).child("ETAPA 3").child("RESPOSTA4").setValue(valor4);
                usuarioReferencia.child(idUserC).child("ETAPA 3").child("RESPOSTA5").setValue(valor5);
                usuarioReferencia.child(idUserC).child("ETAPA 3").child("RESPOSTA6").setValue(valor6);
                usuarioReferencia.child(idUserC).child("ETAPA 3").child("RESPOSTA7").setValue(valor7);

            }if (etapa == 4){
                usuarioReferencia.child(idUserC).child("ETAPA 4").child("RESPOSTA1").setValue(valor1);
                usuarioReferencia.child(idUserC).child("ETAPA 4").child("RESPOSTA2").setValue(valor2);
                usuarioReferencia.child(idUserC).child("ETAPA 4").child("RESPOSTA3").setValue(valor3);
                usuarioReferencia.child(idUserC).child("ETAPA 4").child("RESPOSTA4").setValue(valor4);
                usuarioReferencia.child(idUserC).child("ETAPA 4").child("RESPOSTA5").setValue(valor5);
                usuarioReferencia.child(idUserC).child("ETAPA 4").child("RESPOSTA6").setValue(valor6);
                usuarioReferencia.child(idUserC).child("ETAPA 4").child("RESPOSTA7").setValue(valor7);

            }if (etapa == 5){
                usuarioReferencia.child(idUserC).child("ETAPA 5").child("RESPOSTA1").setValue(valor1);
                usuarioReferencia.child(idUserC).child("ETAPA 5").child("RESPOSTA2").setValue(valor2);
                usuarioReferencia.child(idUserC).child("ETAPA 5").child("RESPOSTA3").setValue(valor3);
                usuarioReferencia.child(idUserC).child("ETAPA 5").child("RESPOSTA4").setValue(valor4);
                usuarioReferencia.child(idUserC).child("ETAPA 5").child("RESPOSTA5").setValue(valor5);
                usuarioReferencia.child(idUserC).child("ETAPA 5").child("RESPOSTA6").setValue(valor6);
                usuarioReferencia.child(idUserC).child("ETAPA 5").child("RESPOSTA7").setValue(valor7);

            }if (etapa == 6){
                usuarioReferencia.child(idUserC).child("ETAPA 6").child("RESPOSTA1").setValue(valor1);
                usuarioReferencia.child(idUserC).child("ETAPA 6").child("RESPOSTA2").setValue(valor2);
            usuarioReferencia.child(idUserC).child("ETAPA 6").child("RESPOSTA3").setValue(valor3);
            usuarioReferencia.child(idUserC).child("ETAPA 6").child("RESPOSTA4").setValue(valor4);
            usuarioReferencia.child(idUserC).child("ETAPA 6").child("RESPOSTA5").setValue(valor5);
            usuarioReferencia.child(idUserC).child("ETAPA 6").child("RESPOSTA6").setValue(valor6);
            usuarioReferencia.child(idUserC).child("ETAPA 6").child("RESPOSTA7").setValue(valor7);

        }if (etapa == 7){
            usuarioReferencia.child(idUserC).child("ETAPA 7").child("RESPOSTA1").setValue(valor1);
            usuarioReferencia.child(idUserC).child("ETAPA 7").child("RESPOSTA2").setValue(valor2);
            usuarioReferencia.child(idUserC).child("ETAPA 7").child("RESPOSTA3").setValue(valor3);
            usuarioReferencia.child(idUserC).child("ETAPA 7").child("RESPOSTA4").setValue(valor4);
            usuarioReferencia.child(idUserC).child("ETAPA 7").child("RESPOSTA5").setValue(valor5);
            usuarioReferencia.child(idUserC).child("ETAPA 7").child("RESPOSTA6").setValue(valor6);
            usuarioReferencia.child(idUserC).child("ETAPA 7").child("RESPOSTA7").setValue(valor7);

        }if (etapa == 8){
            usuarioReferencia.child(idUserC).child("ETAPA 8").child("RESPOSTA1").setValue(valor1);
            usuarioReferencia.child(idUserC).child("ETAPA 8").child("RESPOSTA2").setValue(valor2);
            usuarioReferencia.child(idUserC).child("ETAPA 8").child("RESPOSTA3").setValue(valor3);
            usuarioReferencia.child(idUserC).child("ETAPA 8").child("RESPOSTA4").setValue(valor4);
            usuarioReferencia.child(idUserC).child("ETAPA 8").child("RESPOSTA5").setValue(valor5);
            usuarioReferencia.child(idUserC).child("ETAPA 8").child("RESPOSTA6").setValue(valor6);
            usuarioReferencia.child(idUserC).child("ETAPA 8").child("RESPOSTA7").setValue(valor7);

        }if (etapa == 9){
            usuarioReferencia.child(idUserC).child("ETAPA 9").child("RESPOSTA1").setValue(valor1);
            usuarioReferencia.child(idUserC).child("ETAPA 9").child("RESPOSTA2").setValue(valor2);
            usuarioReferencia.child(idUserC).child("ETAPA 9").child("RESPOSTA3").setValue(valor3);
            usuarioReferencia.child(idUserC).child("ETAPA 9").child("RESPOSTA4").setValue(valor4);
            usuarioReferencia.child(idUserC).child("ETAPA 9").child("RESPOSTA5").setValue(valor5);
            usuarioReferencia.child(idUserC).child("ETAPA 9").child("RESPOSTA6").setValue(valor6);
            usuarioReferencia.child(idUserC).child("ETAPA 9").child("RESPOSTA7").setValue(valor7);

        }if (etapa == 10){
            usuarioReferencia.child(idUserC).child("ETAPA 10").child("RESPOSTA1").setValue(valor1);
            usuarioReferencia.child(idUserC).child("ETAPA 10").child("RESPOSTA2").setValue(valor2);
            usuarioReferencia.child(idUserC).child("ETAPA 10").child("RESPOSTA3").setValue(valor3);
            usuarioReferencia.child(idUserC).child("ETAPA 10").child("RESPOSTA4").setValue(valor4);
            usuarioReferencia.child(idUserC).child("ETAPA 10").child("RESPOSTA5").setValue(valor5);
            usuarioReferencia.child(idUserC).child("ETAPA 10").child("RESPOSTA6").setValue(valor6);
            usuarioReferencia.child(idUserC).child("ETAPA 10").child("RESPOSTA7").setValue(valor7);

        }

        /*
        try {
            //usar variaveis no banco SQlite
            if (valor7 < 0 || valor7> 7) {

                Toast.makeText(TelaInicial.this, "Ocorreu Uma Falha Grave ! Consulte o Suporte", Toast.LENGTH_SHORT ).show();

            } else{

                Log.i("Resultado - ","ORDEM EM SALVAR : " + etapa +valor1 + valor2 + valor3 + valor4 + valor5 + valor6 + valor7);
               bancoDados.execSQL("INSERT INTO  notas(id, respPergunta1, respPergunta2, respPergunta3,respPergunta4, respPergunta5, respPergunta6, respPergunta7) VALUES (("+etapa+") ,("+valor1+") ,("+valor2+") ,("+valor3+") ,(" +valor4+ ") ,(" +valor5+ "),("+valor6+") ,(" +valor7+ ")) ");

            }

        }
        catch (Exception e){

            e.printStackTrace();

        }*/
    }




    ///////////////////////////RECUPERAR DADOS DO SQLITE////////////////////////////////////////

    private void recuperarTarefas(){



        ocultarLayout(7);
        usuarioReferencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ocultarLayout(7);
                if(dataSnapshot.child(idUserC).child("ETAPA 1").exists()){
                    if(contafaz == true){
                    passT = true;}
                    if(dataSnapshot.child(idUserC).child("ETAPA 2").exists()){
                        if(dataSnapshot.child(idUserC).child("ETAPA 3").exists()){
                            if(dataSnapshot.child(idUserC).child("ETAPA 4").exists()){
                                if(dataSnapshot.child(idUserC).child("ETAPA 5").exists()){
                                    if(dataSnapshot.child(idUserC).child("ETAPA 6").exists()){
                                        if(dataSnapshot.child(idUserC).child("ETAPA 7").exists()){
                                            if(dataSnapshot.child(idUserC).child("ETAPA 8").exists()){
                                                if(dataSnapshot.child(idUserC).child("ETAPA 9").exists()){
                                                    if(dataSnapshot.child(idUserC).child("ETAPA 10").exists()){
                                                        ocultarLayout(7);
                                                        estadoAtualEtapa = 11;
                                                        limparResposta(0);
                                                        calcTotal();
                                                        ocultarLayout(6);
                                                        estadoAtualEtapaR =11 ;
                                                        contador = 1;
                                                        atualizarPergunta();
                                                    //
                                                        if(estadoAtualEtapa == 11 && dataSnapshot.child(idUserC).child("TOTAL").exists()){
                                                            ocultarLayout(8);
                                                        confNome.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                String nmk = edNome.getText().toString();
                                                                if (TextUtils.isEmpty(nmk)) {
                                                                    edNome.setError("Por favor digite seu nome!");


                                                                } else {
                                                                    usuarioReferencia.child(idUserC).child("DADOS").child("NOMEDIGITADO").setValue(edNome.getText().toString());
                                                                    usuarioReferencia.child(idUserC).child("DADOS").child("NOMECONTA").setValue(nomeUserC);
                                                                    usuarioReferencia.child(idUserC).child("DADOS").child("EMAILCONTA").setValue(emailUserC);

                                                                }






                                                            }});}

                                                    //
                                                        }

                                                        else{
                                                        ocultarLayout(7);
                                                    estadoAtualEtapa = 10;
                                                    limparResposta(0);
                                                    ocultarLayout(6);
                                                    estadoAtualEtapaR =10 ;
                                                    contador = 1;
                                                    atualizarPergunta();}

                                                } else{

                                                    ocultarLayout(7);
                                                estadoAtualEtapa = 9;
                                                limparResposta(0);
                                                ocultarLayout(6);
                                                estadoAtualEtapaR =9 ;
                                                contador = 1;
                                                atualizarPergunta();}
                                            }else{
                                            ocultarLayout(7);
                                            estadoAtualEtapa = 8;
                                            limparResposta(0);
                                            ocultarLayout(6);
                                            estadoAtualEtapaR =8 ;
                                            contador = 1;
                                            atualizarPergunta();}

                                        }else {
                                            ocultarLayout(7);
                                            estadoAtualEtapa = 7;
                                            limparResposta(0);
                                            ocultarLayout(6);
                                            estadoAtualEtapaR = 7;
                                            contador = 1;
                                            atualizarPergunta();
                                        }
                                            }else{
                                    ocultarLayout(7);
                                    estadoAtualEtapa = 6;
                                    limparResposta(0);
                                    ocultarLayout(6);
                                    estadoAtualEtapaR =6 ;
                                    contador = 1;
                                    atualizarPergunta();
                                }

                                } else{


                                ocultarLayout(7);
                                estadoAtualEtapa = 5;
                                limparResposta(0);
                                ocultarLayout(6);
                                estadoAtualEtapaR =5 ;
                                contador = 1;
                                atualizarPergunta();
                                }
                            }else {
                                ocultarLayout(7);
                                estadoAtualEtapa = 4;
                                limparResposta(0);
                                ocultarLayout(6);
                                estadoAtualEtapaR = 4;
                                contador = 1;
                                atualizarPergunta();
                            }
                        }
                        else{
                        ocultarLayout(7);
                        estadoAtualEtapa = 3;
                        limparResposta(0);
                        ocultarLayout(6);
                        estadoAtualEtapaR = 3 ;
                        contador = 1;
                        atualizarPergunta();}

                    }  else{

                        estadoAtualEtapa = 2;
                        limparResposta(0);
                        ocultarLayout(6);
                        estadoAtualEtapaR =2 ;
                        contador = 1;
                        atualizarPergunta();}



                    }

                else {
                    estadoAtualEtapa = 0 ;
                    ocultarLayout(2);
                    contador = 1;
                }


                    if ( dataSnapshot.child(idUserC).child("DADOS").child("NOMEDIGITADO").exists()){
                //////////////////////////////
                mairCalc mairCalc = new mairCalc(
                        Integer.parseInt(dataSnapshot.child(idUserC).child("TOTAL").child("inteligencia 1").getValue().toString())
                        ,Integer.parseInt(dataSnapshot.child(idUserC).child("TOTAL").child("inteligencia 2").getValue().toString())
                        ,Integer.parseInt(dataSnapshot.child(idUserC).child("TOTAL").child("inteligencia 3").getValue().toString())
                        ,Integer.parseInt(dataSnapshot.child(idUserC).child("TOTAL").child("inteligencia 4").getValue().toString())
                        ,Integer.parseInt(dataSnapshot.child(idUserC).child("TOTAL").child("inteligencia 5").getValue().toString())
                        ,Integer.parseInt(dataSnapshot.child(idUserC).child("TOTAL").child("inteligencia 6").getValue().toString())
                        ,Integer.parseInt(dataSnapshot.child(idUserC).child("TOTAL").child("inteligencia 7").getValue().toString()));


                mairCalc.calcularN();
                if (mairCalc.getNota1MN() == 1){
                    textInteliResult.setText(mairCalc.getMaiorNota()+"Cinestesica, Atores, atletas, mimicos, artistas circenses e dancarinos profissionais");
                } else if (mairCalc.getNota1MN() == 2){
                    textInteliResult.setText(mairCalc.getMaiorNota()+" Pontos : "+ "Espacial ,Engenheiros, Escultores, Cirurgioes plasticos, artistas, graficos e arquitetos");
                } else if (mairCalc.getNota1MN() == 3){
                    textInteliResult.setText(mairCalc.getMaiorNota()+" Pontos : "+ "Interpessoal, Professores, terapeutas,politicos e lideres religiosos");
                } else if (mairCalc.getNota1MN() == 4){
                    textInteliResult.setText(mairCalc.getMaiorNota()+" Pontos : "+ "intrapessoal, Filosofos, psicologos e pesquisadores de padroes de cognição");
                } else if (mairCalc.getNota1MN() == 5){
                    textInteliResult.setText(mairCalc.getMaiorNota()+" Pontos : "+ "Linguistica, Poetas, Teatrologos, Escritores, novelistas, oradores e comediantes");
                } else if (mairCalc.getNota1MN() == 6 ){
                    textInteliResult.setText(mairCalc.getMaiorNota()+" Pontos : "+ "Logico, Cientistas, Programadoes de Computadores, Contadores, Matematica ,Advogados, Banqueiros e Matematicos. ");
                } else if (mairCalc.getNota1MN() == 7){
                    textInteliResult.setText(mairCalc.getMaiorNota()+" Pontos : "+ "Musical, Musicos, Cantores, Compositores e Maestro. ");
                }
                // segunda nota


                if (mairCalc.getNota2MN() == 1){
                    textInteliResult2.setText(mairCalc.getMaiorNota2()+" Pontos : "+ "Cinestesica, Atores, atletas, mimicos, artistas circenses e dancarinos profissionais");
                } else if (mairCalc.getNota2MN() == 2){
                    textInteliResult2.setText(mairCalc.getMaiorNota2()+" Pontos : "+ "Espacial ,Engenheiros, Escultores, Cirurgioes plasticos, artistas, graficos e arquitetos");
                } else if (mairCalc.getNota2MN() == 3){
                    textInteliResult2.setText(mairCalc.getMaiorNota2()+" Pontos : "+ "Interpessoal, Professores, terapeutas,politicos e lideres religiosos");
                } else if (mairCalc.getNota2MN() == 4){
                    textInteliResult2.setText(mairCalc.getMaiorNota2()+" Pontos : "+ "intrapessoal, Filosofos, psicologos e pesquisadores de padroes de cognição");
                } else if (mairCalc.getNota2MN() == 5){
                    textInteliResult2.setText(mairCalc.getMaiorNota2()+" Pontos : "+ "Linguistica, Poetas, Teatrologos, Escritores, novelistas, oradores e comediantes");
                } else if (mairCalc.getNota2MN() == 6 ){
                    textInteliResult2.setText(mairCalc.getMaiorNota2()+" Pontos : "+ "Logico, Cientistas, Programadoes de Computadores, Contadores, Matematica ,Advogados, Banqueiros e Matematicos. ");
                } else if (mairCalc.getNota2MN() == 7){
                    textInteliResult2.setText(mairCalc.getMaiorNota2()+" Pontos : "+ "Musical, Musicos, Cantores, Compositores e Maestro. ");
                }
                ////////////////////
                ocultarLayout(1);
                    }












                    }




            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void calcTotal(){
        usuarioReferencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usuarioReferencia.child(idUserC).child("TOTAL").child("inteligencia 1").setValue(
                        Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 1").child("RESPOSTA1").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 2").child("RESPOSTA1").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 3").child("RESPOSTA1").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 4").child("RESPOSTA1").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 5").child("RESPOSTA1").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 6").child("RESPOSTA1").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 7").child("RESPOSTA1").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 8").child("RESPOSTA1").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 9").child("RESPOSTA1").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 10").child("RESPOSTA1").getValue().toString()));
                //prxima


                usuarioReferencia.child(idUserC).child("TOTAL").child("inteligencia 2").setValue(
                        Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 1").child("RESPOSTA2").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 2").child("RESPOSTA2").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 3").child("RESPOSTA2").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 4").child("RESPOSTA2").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 5").child("RESPOSTA2").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 6").child("RESPOSTA2").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 7").child("RESPOSTA2").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 8").child("RESPOSTA2").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 9").child("RESPOSTA2").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 10").child("RESPOSTA2").getValue().toString()));

                //prxima


                usuarioReferencia.child(idUserC).child("TOTAL").child("inteligencia 3").setValue(
                        Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 1").child("RESPOSTA3").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 2").child("RESPOSTA3").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 3").child("RESPOSTA3").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 4").child("RESPOSTA3").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 5").child("RESPOSTA3").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 6").child("RESPOSTA3").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 7").child("RESPOSTA3").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 8").child("RESPOSTA3").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 9").child("RESPOSTA3").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 10").child("RESPOSTA3").getValue().toString()));

                //prxima


                usuarioReferencia.child(idUserC).child("TOTAL").child("inteligencia 4").setValue(
                        Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 1").child("RESPOSTA4").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 2").child("RESPOSTA4").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 3").child("RESPOSTA4").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 4").child("RESPOSTA4").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 5").child("RESPOSTA4").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 6").child("RESPOSTA4").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 7").child("RESPOSTA4").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 8").child("RESPOSTA4").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 9").child("RESPOSTA4").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 10").child("RESPOSTA4").getValue().toString()));

                //prxima


                usuarioReferencia.child(idUserC).child("TOTAL").child("inteligencia 5").setValue(
                        Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 1").child("RESPOSTA5").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 2").child("RESPOSTA5").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 3").child("RESPOSTA5").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 4").child("RESPOSTA5").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 5").child("RESPOSTA5").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 6").child("RESPOSTA5").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 7").child("RESPOSTA5").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 8").child("RESPOSTA5").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 9").child("RESPOSTA5").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 10").child("RESPOSTA5").getValue().toString()));

                //prxima


                usuarioReferencia.child(idUserC).child("TOTAL").child("inteligencia 6").setValue(
                        Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 1").child("RESPOSTA6").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 2").child("RESPOSTA6").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 3").child("RESPOSTA6").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 4").child("RESPOSTA6").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 5").child("RESPOSTA6").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 6").child("RESPOSTA6").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 7").child("RESPOSTA6").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 8").child("RESPOSTA6").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 9").child("RESPOSTA6").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 10").child("RESPOSTA6").getValue().toString()));

                //prxima


                usuarioReferencia.child(idUserC).child("TOTAL").child("inteligencia 7").setValue(
                        Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 1").child("RESPOSTA7").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 2").child("RESPOSTA7").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 3").child("RESPOSTA7").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 4").child("RESPOSTA7").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 5").child("RESPOSTA7").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 6").child("RESPOSTA7").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 7").child("RESPOSTA7").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 8").child("RESPOSTA7").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 9").child("RESPOSTA7").getValue().toString())+
                                Integer.parseInt(dataSnapshot.child(idUserC).child("ETAPA 10").child("RESPOSTA7").getValue().toString()));







            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void recuperarTarefas2(){



        try{

            ///////////////////////////////////////////////////////


            if (idUserC != null){


                usuarioReferencia.addValueEventListener(new ValueEventListener() {



                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {









                        if(dataSnapshot.child(idUserC).child("TOTAL").exists()){
                            ocultarLayout(8);
                            confNome.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (edNome.getText().toString() != null && edNome.getText().toString() != ""){
                                        usuarioReferencia.child(idUserC).child("DADOS").child("NOMEDIGITADO").setValue(edNome.getText().toString());

                                    }else {
                                        Toast.makeText(getApplicationContext(),"Digite Seu Nome",Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });


                            usuarioReferencia.child(idUserC).child("DADOS").child("NOMECONTA").setValue(nomeUserC);
                            usuarioReferencia.child(idUserC).child("DADOS").child("EMAILCONTA").setValue(emailUserC);

                            if(dataSnapshot.child(idUserC).child("DADOS").child("NOMEDIGITADO").exists()){
                                ocultarLayout(1);}






                            //cobt.setText("MAIOR 1 " + mairCalc.getMaiorNota() + "MAIOR 2 " + mairCalc.getMaiorNota2() );

                        }








                    };

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




            }

            //////////////////////////////////////////////////////







            int contadortot =0;
            //instanciar o Cursor
            //usado para recuperar linhas e manipular               //ordenando pelo decrecente usando DESC

            Cursor cursor = bancoDados.rawQuery("SELECT * FROM notas ORDER BY id DESC", null);
            //recuperar os ids das colunas
            int indiceColunaId = cursor.getColumnIndex("id");
            int indiceColunarespPergunta1 = cursor.getColumnIndex("respPergunta1");
            int indiceColunarespPergunta2 = cursor.getColumnIndex("respPergunta2");
            int indiceColunarespPergunta3 = cursor.getColumnIndex("respPergunta3");
            int indiceColunarespPergunta4 = cursor.getColumnIndex("respPergunta4");
            int indiceColunarespPergunta5 = cursor.getColumnIndex("respPergunta5");
            int indiceColunarespPergunta6 = cursor.getColumnIndex("respPergunta6");
            int indiceColunarespPergunta7 = cursor.getColumnIndex("respPergunta7");
            //listar as tarefas
            cursor.moveToFirst();

            gerRespostaPerg1 =0;
            gerRespostaPerg2 =0;
            gerRespostaPerg3 =0;
            gerRespostaPerg4 =0;
            gerRespostaPerg5 =0;
            gerRespostaPerg6 =0;
            gerRespostaPerg7 =0;


            while(cursor != null ){



                Log.i("|Resultado| - ","ID : " + cursor.getString(indiceColunaId));
                Log.i("Resultado - ","Id p1: " + cursor.getString(indiceColunarespPergunta1));
                Log.i("Resultado - ","Id p2: " + cursor.getString(indiceColunarespPergunta2));
                Log.i("Resultado - ","Id p3: " + cursor.getString(indiceColunarespPergunta3));
                Log.i("Resultado - ","Id p4: " + cursor.getString(indiceColunarespPergunta4));
                Log.i("Resultado - ","Id p5: " + cursor.getString(indiceColunarespPergunta5));
                Log.i("Resultado - ","Id p6: " + cursor.getString(indiceColunarespPergunta6));
                Log.i("Resultado - ","Id p7: " + cursor.getString(indiceColunarespPergunta7));

                if (contadortot <=10) {
                    gerRespostaPerg1 += cursor.getInt(indiceColunarespPergunta1);
                    gerRespostaPerg2 += cursor.getInt(indiceColunarespPergunta2);
                    gerRespostaPerg3 += cursor.getInt(indiceColunarespPergunta3);
                    gerRespostaPerg4 += cursor.getInt(indiceColunarespPergunta4);
                    gerRespostaPerg5 += cursor.getInt(indiceColunarespPergunta5);
                    gerRespostaPerg6 += cursor.getInt(indiceColunarespPergunta6);
                    gerRespostaPerg7 += cursor.getInt(indiceColunarespPergunta7);
                    contadortot++;
                    if (contadortot == 0){
                        estadoAtualEtapaR = 0;
                        ocultarLayout(2);
                    }
                    if (contadortot == 10){
                        estadoAtualEtapaR = 10;
                        contador = 1;

                        usuarioReferencia.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {





                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });





                    }
                    if (contadortot == 2){
                        estadoAtualEtapa = 3;
                        limparResposta(0);
                        ocultarLayout(6);
                        atualizarPergunta();
                        estadoAtualEtapaR = 2;
                        contador = 1;
                    }

                    if (contadortot == 3){
                        estadoAtualEtapa = 4;
                        limparResposta(0);
                        ocultarLayout(6);
                        atualizarPergunta();
                        estadoAtualEtapaR = 3;
                        contador = 1;
                    }

                    if (contadortot == 4){
                        estadoAtualEtapa = 5;
                        limparResposta(0);
                        ocultarLayout(6);
                        atualizarPergunta();

                        estadoAtualEtapaR = 4;
                        contador = 1;
                    }

                    if (contadortot == 5){
                        estadoAtualEtapa = 6;
                        limparResposta(0);
                        ocultarLayout(6);
                        atualizarPergunta();

                        estadoAtualEtapaR = 5;
                    }

                    if (contadortot == 6){
                        estadoAtualEtapa = 7;
                        limparResposta(0);
                        ocultarLayout(6);
                        atualizarPergunta();

                        estadoAtualEtapaR = 6;
                    }

                    if (contadortot == 7){

                        estadoAtualEtapa = 8;
                        limparResposta(0);
                        ocultarLayout(6);
                        atualizarPergunta();

                        estadoAtualEtapaR = 7;
                    }

                    if (contadortot == 8){
                        estadoAtualEtapa = 9;
                        limparResposta(0);
                        ocultarLayout(6);
                        atualizarPergunta();

                        estadoAtualEtapaR = 8;
                    }


                    if (contadortot == 9){
                        estadoAtualEtapa = 10;
                        limparResposta(0);
                        ocultarLayout(6);
                        atualizarPergunta();

                        estadoAtualEtapaR = 9;
                    }
                    if (contadortot == 10){
                        estadoAtualEtapaR = 10;
                        contador = 1;
                    }
                    if (contadortot == 1){

                        estadoAtualEtapa = 2;
                        limparResposta(0);
                        ocultarLayout(6);
                        atualizarPergunta();
                        estadoAtualEtapaR =1 ;
                        contador = 1;
                    }

                }
                if (cursor.getInt(indiceColunaId ) == 1){
                    usuarioReferencia.child(idUserC).child("ETAPA 1").child("resposta1").setValue(cursor.getInt(indiceColunarespPergunta1));
                    usuarioReferencia.child(idUserC).child("ETAPA 1").child("resposta2").setValue(cursor.getInt(indiceColunarespPergunta2));
                    usuarioReferencia.child(idUserC).child("ETAPA 1").child("resposta3").setValue(cursor.getInt(indiceColunarespPergunta3));
                    usuarioReferencia.child(idUserC).child("ETAPA 1").child("resposta4").setValue(cursor.getInt(indiceColunarespPergunta4));
                    usuarioReferencia.child(idUserC).child("ETAPA 1").child("resposta5").setValue(cursor.getInt(indiceColunarespPergunta5));
                    usuarioReferencia.child(idUserC).child("ETAPA 1").child("resposta6").setValue(cursor.getInt(indiceColunarespPergunta6));
                    usuarioReferencia.child(idUserC).child("ETAPA 1").child("resposta7").setValue(cursor.getInt(indiceColunarespPergunta7));

                }
                if (cursor.getInt(indiceColunaId ) == 2){
                    usuarioReferencia.child(idUserC).child("ETAPA 2").child("resposta1").setValue(cursor.getInt(indiceColunarespPergunta1));
                    usuarioReferencia.child(idUserC).child("ETAPA 2").child("resposta2").setValue(cursor.getInt(indiceColunarespPergunta2));
                    usuarioReferencia.child(idUserC).child("ETAPA 2").child("resposta3").setValue(cursor.getInt(indiceColunarespPergunta3));
                    usuarioReferencia.child(idUserC).child("ETAPA 2").child("resposta4").setValue(cursor.getInt(indiceColunarespPergunta4));
                    usuarioReferencia.child(idUserC).child("ETAPA 2").child("resposta5").setValue(cursor.getInt(indiceColunarespPergunta5));
                    usuarioReferencia.child(idUserC).child("ETAPA 2").child("resposta6").setValue(cursor.getInt(indiceColunarespPergunta6));
                    usuarioReferencia.child(idUserC).child("ETAPA 2").child("resposta7").setValue(cursor.getInt(indiceColunarespPergunta7));
                    estadoAtualEtapa = 2;
                    contador=1;
                }
                if (cursor.getInt(indiceColunaId ) == 3){
                    usuarioReferencia.child(idUserC).child("ETAPA 3").child("resposta1").setValue(cursor.getInt(indiceColunarespPergunta1));
                    usuarioReferencia.child(idUserC).child("ETAPA 3").child("resposta2").setValue(cursor.getInt(indiceColunarespPergunta2));
                    usuarioReferencia.child(idUserC).child("ETAPA 3").child("resposta3").setValue(cursor.getInt(indiceColunarespPergunta3));
                    usuarioReferencia.child(idUserC).child("ETAPA 3").child("resposta4").setValue(cursor.getInt(indiceColunarespPergunta4));
                    usuarioReferencia.child(idUserC).child("ETAPA 3").child("resposta5").setValue(cursor.getInt(indiceColunarespPergunta5));
                    usuarioReferencia.child(idUserC).child("ETAPA 3").child("resposta6").setValue(cursor.getInt(indiceColunarespPergunta6));
                    usuarioReferencia.child(idUserC).child("ETAPA 3").child("resposta7").setValue(cursor.getInt(indiceColunarespPergunta7));
                    estadoAtualEtapa = 3;
                    contador=1;
                }
                if (cursor.getInt(indiceColunaId ) == 4){
                    usuarioReferencia.child(idUserC).child("ETAPA 4").child("resposta1").setValue(cursor.getInt(indiceColunarespPergunta1));
                    usuarioReferencia.child(idUserC).child("ETAPA 4").child("resposta2").setValue(cursor.getInt(indiceColunarespPergunta2));
                    usuarioReferencia.child(idUserC).child("ETAPA 4").child("resposta3").setValue(cursor.getInt(indiceColunarespPergunta3));
                    usuarioReferencia.child(idUserC).child("ETAPA 4").child("resposta4").setValue(cursor.getInt(indiceColunarespPergunta4));
                    usuarioReferencia.child(idUserC).child("ETAPA 4").child("resposta5").setValue(cursor.getInt(indiceColunarespPergunta5));
                    usuarioReferencia.child(idUserC).child("ETAPA 4").child("resposta6").setValue(cursor.getInt(indiceColunarespPergunta6));
                    usuarioReferencia.child(idUserC).child("ETAPA 4").child("resposta7").setValue(cursor.getInt(indiceColunarespPergunta7));
                    estadoAtualEtapa = 4;
                    contador=1;
                }
                if (cursor.getInt(indiceColunaId ) == 5){
                    usuarioReferencia.child(idUserC).child("ETAPA 5").child("resposta1").setValue(cursor.getInt(indiceColunarespPergunta1));
                    usuarioReferencia.child(idUserC).child("ETAPA 5").child("resposta2").setValue(cursor.getInt(indiceColunarespPergunta2));
                    usuarioReferencia.child(idUserC).child("ETAPA 5").child("resposta3").setValue(cursor.getInt(indiceColunarespPergunta3));
                    usuarioReferencia.child(idUserC).child("ETAPA 5").child("resposta4").setValue(cursor.getInt(indiceColunarespPergunta4));
                    usuarioReferencia.child(idUserC).child("ETAPA 5").child("resposta5").setValue(cursor.getInt(indiceColunarespPergunta5));
                    usuarioReferencia.child(idUserC).child("ETAPA 5").child("resposta6").setValue(cursor.getInt(indiceColunarespPergunta6));
                    usuarioReferencia.child(idUserC).child("ETAPA 5").child("resposta7").setValue(cursor.getInt(indiceColunarespPergunta7));
                    estadoAtualEtapa = 5;
                    contador=1;
                }
                if (cursor.getInt(indiceColunaId ) == 6){
                    usuarioReferencia.child(idUserC).child("ETAPA 6").child("resposta1").setValue(cursor.getInt(indiceColunarespPergunta1));
                    usuarioReferencia.child(idUserC).child("ETAPA 6").child("resposta2").setValue(cursor.getInt(indiceColunarespPergunta2));
                    usuarioReferencia.child(idUserC).child("ETAPA 6").child("resposta3").setValue(cursor.getInt(indiceColunarespPergunta3));
                    usuarioReferencia.child(idUserC).child("ETAPA 6").child("resposta4").setValue(cursor.getInt(indiceColunarespPergunta4));
                    usuarioReferencia.child(idUserC).child("ETAPA 6").child("resposta5").setValue(cursor.getInt(indiceColunarespPergunta5));
                    usuarioReferencia.child(idUserC).child("ETAPA 6").child("resposta6").setValue(cursor.getInt(indiceColunarespPergunta6));
                    usuarioReferencia.child(idUserC).child("ETAPA 6").child("resposta7").setValue(cursor.getInt(indiceColunarespPergunta7));
                    estadoAtualEtapa = 6;
                    contador=1;
                }
                if (cursor.getInt(indiceColunaId ) == 7){
                    usuarioReferencia.child(idUserC).child("ETAPA 7").child("resposta1").setValue(cursor.getInt(indiceColunarespPergunta1));
                    usuarioReferencia.child(idUserC).child("ETAPA 7").child("resposta2").setValue(cursor.getInt(indiceColunarespPergunta2));
                    usuarioReferencia.child(idUserC).child("ETAPA 7").child("resposta3").setValue(cursor.getInt(indiceColunarespPergunta3));
                    usuarioReferencia.child(idUserC).child("ETAPA 7").child("resposta4").setValue(cursor.getInt(indiceColunarespPergunta4));
                    usuarioReferencia.child(idUserC).child("ETAPA 7").child("resposta5").setValue(cursor.getInt(indiceColunarespPergunta5));
                    usuarioReferencia.child(idUserC).child("ETAPA 7").child("resposta6").setValue(cursor.getInt(indiceColunarespPergunta6));
                    usuarioReferencia.child(idUserC).child("ETAPA 7").child("resposta7").setValue(cursor.getInt(indiceColunarespPergunta7));
                    estadoAtualEtapa = 7;
                    contador=1;
                }
                if (cursor.getInt(indiceColunaId ) == 8){
                    usuarioReferencia.child(idUserC).child("ETAPA 8").child("resposta1").setValue(cursor.getInt(indiceColunarespPergunta1));
                    usuarioReferencia.child(idUserC).child("ETAPA 8").child("resposta2").setValue(cursor.getInt(indiceColunarespPergunta2));
                    usuarioReferencia.child(idUserC).child("ETAPA 8").child("resposta3").setValue(cursor.getInt(indiceColunarespPergunta3));
                    usuarioReferencia.child(idUserC).child("ETAPA 8").child("resposta4").setValue(cursor.getInt(indiceColunarespPergunta4));
                    usuarioReferencia.child(idUserC).child("ETAPA 8").child("resposta5").setValue(cursor.getInt(indiceColunarespPergunta5));
                    usuarioReferencia.child(idUserC).child("ETAPA 8").child("resposta6").setValue(cursor.getInt(indiceColunarespPergunta6));
                    usuarioReferencia.child(idUserC).child("ETAPA 8").child("resposta7").setValue(cursor.getInt(indiceColunarespPergunta7));
                    estadoAtualEtapa = 8;
                    contador=1;
                }
                if (cursor.getInt(indiceColunaId ) == 9){
                    usuarioReferencia.child(idUserC).child("ETAPA 9").child("resposta1").setValue(cursor.getInt(indiceColunarespPergunta1));
                    usuarioReferencia.child(idUserC).child("ETAPA 9").child("resposta2").setValue(cursor.getInt(indiceColunarespPergunta2));
                    usuarioReferencia.child(idUserC).child("ETAPA 9").child("resposta3").setValue(cursor.getInt(indiceColunarespPergunta3));
                    usuarioReferencia.child(idUserC).child("ETAPA 9").child("resposta4").setValue(cursor.getInt(indiceColunarespPergunta4));
                    usuarioReferencia.child(idUserC).child("ETAPA 9").child("resposta5").setValue(cursor.getInt(indiceColunarespPergunta5));
                    usuarioReferencia.child(idUserC).child("ETAPA 9").child("resposta6").setValue(cursor.getInt(indiceColunarespPergunta6));
                    usuarioReferencia.child(idUserC).child("ETAPA 9").child("resposta7").setValue(cursor.getInt(indiceColunarespPergunta7));
                    estadoAtualEtapa = 9;
                    contador=1;
                }
                if (cursor.getInt(indiceColunaId ) == 10){
                    usuarioReferencia.child(idUserC).child("ETAPA 10").child("resposta1").setValue(cursor.getInt(indiceColunarespPergunta1));
                    usuarioReferencia.child(idUserC).child("ETAPA 10").child("resposta2").setValue(cursor.getInt(indiceColunarespPergunta2));
                    usuarioReferencia.child(idUserC).child("ETAPA 10").child("resposta3").setValue(cursor.getInt(indiceColunarespPergunta3));
                    usuarioReferencia.child(idUserC).child("ETAPA 10").child("resposta4").setValue(cursor.getInt(indiceColunarespPergunta4));
                    usuarioReferencia.child(idUserC).child("ETAPA 10").child("resposta5").setValue(cursor.getInt(indiceColunarespPergunta5));
                    usuarioReferencia.child(idUserC).child("ETAPA 10").child("resposta6").setValue(cursor.getInt(indiceColunarespPergunta6));
                    usuarioReferencia.child(idUserC).child("ETAPA 10").child("resposta7").setValue(cursor.getInt(indiceColunarespPergunta7));
                    estadoAtualEtapa = 10;
                    contador=1;


                }




                //cobt.setText("DADOS NO BANCO ->> " + gerRespostaPerg1 +" | " + gerRespostaPerg2 +" | " + gerRespostaPerg3 +" | " + gerRespostaPerg4 +" | " + gerRespostaPerg5 +" | " + gerRespostaPerg6 +" | " + gerRespostaPerg7);






                cursor.moveToNext();

            }

            //calcMaior();

        }
        catch (Exception e){
            e.printStackTrace();

        }
        usuarioReferencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(idUserC).child("ETAPA 1").exists()){

                }else {
                    ocultarLayout(2);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    ///NOVOS DOS METODOS DE MOSTRAR AS IMAGENS

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(firebaseAuthListener);

    }

    private void goLogInScreen() {

        Intent intent = new Intent(this,LoguinActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    public void logOut(View view) {
        firebaseAuth.signOut();

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()){
                    goLogInScreen();
                }else{
                    Toast.makeText(getApplicationContext(), R.string.naoFechar_conta,Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void revoke(View view) {
        firebaseAuth.signOut();
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()){
                    goLogInScreen();
                } else{
                    Toast.makeText(getApplicationContext(), R.string.naorevogar_acesso,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), R.string.falha_conexao,Toast.LENGTH_SHORT).show();



    }

    @Override
    protected void onStop(){
        super.onStop();
        if (firebaseAuthListener != null){
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }
    public void refazerT(View view){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        usuarioReferencia.child(idUserC).child("ETAPA 1").removeValue();
        usuarioReferencia.child(idUserC).child("ETAPA 2").removeValue();
        usuarioReferencia.child(idUserC).child("ETAPA 3").removeValue();
        usuarioReferencia.child(idUserC).child("ETAPA 4").removeValue();
        usuarioReferencia.child(idUserC).child("ETAPA 5").removeValue();
        usuarioReferencia.child(idUserC).child("ETAPA 6").removeValue();
        usuarioReferencia.child(idUserC).child("ETAPA 7").removeValue();
        usuarioReferencia.child(idUserC).child("ETAPA 8").removeValue();
        usuarioReferencia.child(idUserC).child("ETAPA 9").removeValue();
        usuarioReferencia.child(idUserC).child("ETAPA 10").removeValue();
        usuarioReferencia.child(idUserC).child("TOTAL").removeValue();

            exibido = true;
            usuarioReferencia.child(idUserC).child("TUTORIAIS").child("REFAZER").setValue("FEITO");

    }




    private void atualizarPergunta(){
        ocultarLayout(6);
        if(estadoAtualEtapa ==1){
            perguntasE1();

        }else if(estadoAtualEtapa ==2){
            perguntasE2();
        }else if(estadoAtualEtapa ==3){
            perguntasE3();
        }else if(estadoAtualEtapa ==4){
            perguntasE4();
        }else if(estadoAtualEtapa ==5){
            perguntasE5();
        }else if(estadoAtualEtapa ==6){
            perguntasE6();
        }else if(estadoAtualEtapa ==7){
            perguntasE7();
        }else if(estadoAtualEtapa ==8){
            perguntasE8();
        }else if(estadoAtualEtapa ==9){
            perguntasE9();
        }else if(estadoAtualEtapa ==10){
            perguntasE10();

        }


    }

    private void chamaProx2() {
        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(findViewById(R.id.perguntasId), "Pergunta", "Aqui fica a pergunta, fique atento a ela")
                        .tintTarget(false)
                        .outerCircleColor(R.color.azulgrad)
                        .titleTextColor(R.color.white)
                        .titleTextSize(24)
                        .descriptionTextColor(R.color.black)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(false)
                        .transparentTarget(false)
                        .targetRadius(90),

                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional
                        chamaProx3();
                    }
                }

        );


    }

    private void chamaProx3() {
        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(findViewById(R.id.emoInfla0), "Emojis", "Aqui esta sua paleta de emojis, responda cada pergunta dando um emoji diferente para cada pergunta de acordo com sua afinidade com a pergunta")
                        .tintTarget(false)
                        .outerCircleColor(R.color.azulgrad)
                        .titleTextColor(R.color.white)
                        .titleTextSize(24)
                        .descriptionTextColor(R.color.black)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(false)
                        .transparentTarget(false)
                        .targetRadius(130),

                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional
                        chamaProx4();
                    }
                }

        );
    }

    private void chamaProx4() {

        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(findViewById(R.id.opcoesSeekId), "Escolha", "Aqui fica a barra de seleção, Mova até chegar no emoji desejado")
                        .tintTarget(false)
                        .outerCircleColor(R.color.azulgrad)
                        .titleTextColor(R.color.white)
                        .titleTextSize(24)
                        .descriptionTextColor(R.color.black)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(false)
                        .transparentTarget(false)
                        .targetRadius(130),

                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional
                        chamaProx5();
                    }
                }

        );


    }

    private void chamaProx5() {

        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(findViewById(R.id.botaoC), "Confirmar Resposta", "Aqui fica o botao para confirmar cada resposta, apos selecionado com a barra acima a resposta desejada , é só pressionar aqui que sera automaticamente levado para proxima pergunta, e ao final de cada etapa sera mudado para confirmar etapa")
                        .tintTarget(false)
                        .outerCircleColor(R.color.azulgrad)
                        .titleTextColor(R.color.white)
                        .titleTextSize(24)
                        .descriptionTextColor(R.color.black)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(false)
                        .transparentTarget(false)
                        .targetRadius(100),

                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional
                        chamaProx6();
                    }
                }

        );

    }

    private void chamaProx6() {

        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(findViewById(R.id.botaoEtapa4), "Navegação", "Aqui ficará gravado o emoji para cada pergunta que respondeu sendo possivel clicar sobre ele e alterar sua resposta e voltar novamente para frente para continuar")
                        .tintTarget(false)
                        .outerCircleColor(R.color.azulgrad)
                        .titleTextColor(R.color.white)
                        .titleTextSize(24)
                        .descriptionTextColor(R.color.black)
                        .textTypeface(Typeface.SANS_SERIF)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .targetRadius(180)
                        .transparentTarget(false)
                        .cancelable(false)

        );

    }


    private  void inflEmoj(int nBr){

        if (nBr == 0){

            stasis.setVisibility(View.VISIBLE);
            inEmoj1.setVisibility(View.GONE);
            inEmoj2.setVisibility(View.GONE);
            inEmoj3.setVisibility(View.GONE);
            inEmoj4.setVisibility(View.GONE);
            inEmoj5.setVisibility(View.GONE);
            inEmoj6.setVisibility(View.GONE);
            inEmoj7.setVisibility(View.GONE);


        }
        if (nBr == 1){

            stasis.setVisibility(View.GONE);
            inEmoj1.setVisibility(View.VISIBLE);
            inEmoj2.setVisibility(View.GONE);
            inEmoj3.setVisibility(View.GONE);
            inEmoj4.setVisibility(View.GONE);
            inEmoj5.setVisibility(View.GONE);
            inEmoj6.setVisibility(View.GONE);
            inEmoj7.setVisibility(View.GONE);


        }
        if (nBr == 2){

            stasis.setVisibility(View.GONE);
            inEmoj1.setVisibility(View.GONE);
            inEmoj2.setVisibility(View.VISIBLE);
            inEmoj3.setVisibility(View.GONE);
            inEmoj4.setVisibility(View.GONE);
            inEmoj5.setVisibility(View.GONE);
            inEmoj6.setVisibility(View.GONE);
            inEmoj7.setVisibility(View.GONE);

        }
        if (nBr == 3){

            stasis.setVisibility(View.GONE);
            inEmoj1.setVisibility(View.GONE);
            inEmoj2.setVisibility(View.GONE);
            inEmoj3.setVisibility(View.VISIBLE);
            inEmoj4.setVisibility(View.GONE);
            inEmoj5.setVisibility(View.GONE);
            inEmoj6.setVisibility(View.GONE);
            inEmoj7.setVisibility(View.GONE);

        }
        if (nBr == 4){

            stasis.setVisibility(View.GONE);
            inEmoj1.setVisibility(View.GONE);
            inEmoj2.setVisibility(View.GONE);
            inEmoj3.setVisibility(View.GONE);
            inEmoj4.setVisibility(View.VISIBLE);
            inEmoj5.setVisibility(View.GONE);
            inEmoj6.setVisibility(View.GONE);
            inEmoj7.setVisibility(View.GONE);

        }
        if (nBr == 5){

            stasis.setVisibility(View.GONE);
            inEmoj1.setVisibility(View.GONE);
            inEmoj2.setVisibility(View.GONE);
            inEmoj3.setVisibility(View.GONE);
            inEmoj4.setVisibility(View.GONE);
            inEmoj5.setVisibility(View.VISIBLE);
            inEmoj6.setVisibility(View.GONE);
            inEmoj7.setVisibility(View.GONE);

        }
        if (nBr == 6){

            stasis.setVisibility(View.GONE);
            inEmoj1.setVisibility(View.GONE);
            inEmoj2.setVisibility(View.GONE);
            inEmoj3.setVisibility(View.GONE);
            inEmoj4.setVisibility(View.GONE);
            inEmoj5.setVisibility(View.GONE);
            inEmoj6.setVisibility(View.VISIBLE);
            inEmoj7.setVisibility(View.GONE);

        }
        if (nBr == 7){

            stasis.setVisibility(View.GONE);
            inEmoj1.setVisibility(View.GONE);
            inEmoj2.setVisibility(View.GONE);
            inEmoj3.setVisibility(View.GONE);
            inEmoj4.setVisibility(View.GONE);
            inEmoj5.setVisibility(View.GONE);
            inEmoj6.setVisibility(View.GONE);
            inEmoj7.setVisibility(View.VISIBLE);

        }

    }
}


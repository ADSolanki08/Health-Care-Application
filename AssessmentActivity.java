package com.example.health;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AssessmentActivity extends AppCompatActivity {
    TextView q1,q2,q3,Mal_per,DenPer,TyPer,CovidPer,ChoPer,ChPer;
    CheckBox Fev,Cou,Wee,Pla,Bod,Hed,Vom,LBP,Dia,Res,Che,Sto,Nst,Bre,Mus,Thi;
    Button MFev,D_CFev,covidFev,MCou,D_TCou,covidCou,Nxt,NxtFnC,NxtF,NxtC;
    LinearLayout Main,Fever,Cough;
    Animation frombottom;
    FrameLayout F;
    double M=0,D=0,T=0,K=0,Co=0,Ch=0,Total=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);

        //FRAME LAYOUT
        F=(FrameLayout)findViewById(R.id.container);
        //PERCENTAGE TEXT VIEW
        Mal_per=(TextView)findViewById(R.id.Mal_Par);
        DenPer=(TextView)findViewById(R.id.Den_Per);
        TyPer=(TextView)findViewById(R.id.Ty_Per);
        ChPer=(TextView)findViewById(R.id.Chi_Per);
        ChoPer=(TextView)findViewById(R.id.Cho_Per);
        CovidPer=(TextView)findViewById(R.id.CoPer);

        //TO CHANGE ACTION BAR TITLE
        getSupportActionBar().setTitle("ASSESSMENT TEST");
        //TO REACH PARENT ACTIVITY
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //LINKING ALL QUESTION WITH OBJ
        q1=(TextView)findViewById(R.id.Q1);
        q2=(TextView)findViewById(R.id.typFev);
        q3=(TextView)findViewById(R.id.typCou);

        //LINKING ALL CHECKBOX WITH OBJ
        Fev=(CheckBox)findViewById(R.id.fevCh);
        Cou=(CheckBox)findViewById(R.id.CoughCh);
        Wee=(CheckBox)findViewById(R.id.WeekCh);
        Pla=(CheckBox)findViewById(R.id.PlaCh);
        Bod=(CheckBox)findViewById(R.id.bjPainCh);
        Hed=(CheckBox)findViewById(R.id.headCh);
        Vom=(CheckBox)findViewById(R.id.VomCh);
        LBP=(CheckBox)findViewById(R.id.LBPCh);
        Dia=(CheckBox)findViewById(R.id.DiaCh);
        Res=(CheckBox)findViewById(R.id.RestCh);
        Che=(CheckBox)findViewById(R.id.CheCh);
        Sto=(CheckBox)findViewById(R.id.StomCh);
        Nst=(CheckBox)findViewById(R.id.NoSnTCh);
        Bre=(CheckBox)findViewById(R.id.BreCh);
        Mus=(CheckBox)findViewById(R.id.MusCh);
        Thi=(CheckBox)findViewById(R.id.ThaCh);

        //LINKING ALL BUTTON WITH OBJ
        Nxt=(Button)findViewById(R.id.NxtBtn);
        NxtFnC=(Button)findViewById(R.id.nxtBtn2);
        NxtF=(Button)findViewById(R.id.nxtBtn4);
        NxtC=(Button)findViewById(R.id.nxtBtn3);
        MFev=(Button)findViewById(R.id.MFev);
        D_CFev=(Button)findViewById(R.id.d_CFev);
        covidFev=(Button)findViewById(R.id.covidFev);
        MCou=(Button)findViewById(R.id.MCou);
        D_TCou=(Button)findViewById(R.id.D_TCou);
        covidCou=(Button)findViewById(R.id.covid_Cou);

        //LINEAR LAYOUT
        Main=(LinearLayout)findViewById(R.id.Main);
        Fever=(LinearLayout)findViewById(R.id.Fe_LL);
        Cough=(LinearLayout)findViewById(R.id.Co_LL);
        final LinearLayout FnC = (LinearLayout) findViewById(R.id.FnC);

        //FOR ANIMATION
        frombottom= AnimationUtils.loadAnimation(this,R.anim.frombottom);


        //NXT BUTTON ON CLICK EVENT
       Nxt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Nxt.setVisibility(View.INVISIBLE);
               F.setVisibility(View.VISIBLE);
               //LOGIC OF THE ASSESSMENT
               Main.animate().translationY(-1500).setDuration(800).setStartDelay(800);

               if(Fev.isChecked())
               {
                   M+=10;
                   D+=10;
                   T+=10;
                   Ch+=10;
                   Co+=10;
                   Total+=10;

               }
               if(Cou.isChecked())
               {
                   M+=10;
                   D+=10;
                   T+=10;
                   Co+=10;
                   Total+=10;
               }
               if(Wee.isChecked())
               {
                   M+=10;
                   T+=10;
                   Total+=10;
               }
               if(Pla.isChecked())
               {
                   D+=15;
                   Total+=10;

               }
               if(Bod.isChecked())
               {
                   D+=10;
                   M+=10;
                   Ch+=10;
                   Total+=10;
               }
               if(Hed.isChecked())
               {
                   M+=10;
                   D+=10;
                   T+=10;
                   Co+=10;
                   Total+=10;


               }
               if(LBP.isChecked())
               {
                   K+=15;
                   Total+=10;
               }
               if(Vom.isChecked())
               {
                   K+=15;
                   Total+=10;
               }
               if(Dia.isChecked())
               {

                   T+=15;
                   Total+=10;
               }
               if(Res.isChecked())
               {

                   K+=15;
                   Total+=10;
               }
               if(Che.isChecked())
               {
                   Co+=15;
                   Total+=10;
               }
               if(Sto.isChecked())
               {
                   T+=15;
                   Total+=10;
               }
               if(Nst.isChecked())
               {
                   Co+=15;
                   Total+=10;
               }
               if(Bre.isChecked())
               {
                   Co+=15;
                   Total+=10;
               }
               if(Mus.isChecked())
               {
                   M+=10;
                   D+=10;
                   K+=10;
                   Total+=10;
               }
               if(Thi.isChecked())
               {
                   K+=15;
                   Total+=15;

               }

               //FIND PERCENTAGE OF ALL DISEASES
               M=(M/Total)*100;
               Co=(Co/Total)*100;
               D=(D/Total)*100;
               T=(T/Total)*100;
               K=(K/Total)*100;
               Ch=(Ch/Total)*100;
                Total=M+Co+D+T+K+Ch;
                M=M=(M/Total)*100;
               Co=(Co/Total)*100;
               D=(D/Total)*100;
               T=(T/Total)*100;
               K=(K/Total)*100;
               Ch=(Ch/Total)*100;
               //All DOUBLE CONVERT INTO STRING FORM
               String Malaria= Double.toString(M);
               String Dengue= Double.toString(D);
               String Typhoid= Double.toString(T);
               String Cholera= Double.toString(K);
               String Chikungunya= Double.toString(Ch);
               String Covid19= Double.toString(Co);

              // getSupportFragmentManager().beginTransaction().replace(R.id.container,new ResultFragment()).commit();
               Mal_per.setText(Malaria+"%");
               DenPer.setText(Dengue+"%");
               TyPer.setText(Typhoid+"%");
               ChoPer.setText(Cholera+"%");
               ChPer.setText(Chikungunya+"%");
               CovidPer.setText(Covid19+"%");




           }
       });

        //





    }
}
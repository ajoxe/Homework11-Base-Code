package com.example.rusili.homework11.detailscreen.view;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rusili.homework11.R;
import com.example.rusili.homework11.detailscreen.model.Pokemon;
import com.example.rusili.homework11.detailscreen.model.objects.Stat;
import com.example.rusili.homework11.detailscreen.model.objects.Stats;
import com.example.rusili.homework11.network.RetrofitFactory;
import com.example.rusili.homework11.pokedexActivity.view.PokedexFragment;

import java.util.Arrays;

public class PokemonDetailActivity extends AppCompatActivity{
	private RetrofitFactory.PokemonNetworkListener pokemonNetworkListener;
	Bundle bundle;
	private String pokemonName;
	private ImageView pokeImage;
	private TextView pokeName;
	private TextView pokeStats;
	private TextView pokeType;
	private String imageURL;
	private Button showFront;
	private Button showBack;
	Context context;

	@Override
	public void onCreate (@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pokemondetail);
		context = this.getApplicationContext();
		initialize();
	}

	private void initialize () {
		pokemonName = getIntent().getStringExtra("pokemon");
		instantiateViews();
		getPokemonDetails();
	}

	private void getPokemonDetails () {
		pokemonNetworkListener = new RetrofitFactory.PokemonNetworkListener() {
			@Override
			public void pokemonCallback (Pokemon pokemon) {
				//TODO: Display pokemon data
				//Hint: Learn how to use Glide to display an image.

				PokedexFragment frag = new PokedexFragment();
				android.support.v4.app.FragmentManager fragM = getSupportFragmentManager();
				FragmentTransaction trans = fragM.beginTransaction();
				Bundle bundle = new Bundle();
				bundle.putString("default","front");
				bundle.putString("pokemon",pokemonName);
				frag.setArguments(bundle);
				trans.replace(R.id.frag_container,frag);
				trans.commit();


				StringBuilder a = new StringBuilder();
				a.append("Type: ");
				a.append(pokemon.getTypes()[0].getType().getName().toUpperCase());
				StringBuilder b = new StringBuilder();
				b.append(pokemonName.toUpperCase());
				StringBuilder c = new StringBuilder();
				c.append("Stats:\n");
				for(int i = 0;i < pokemon.getStats().length;i++){
					c.append(pokemon.getStats()[i].getStat().getName().toUpperCase());
					c.append(": ");
					c.append(pokemon.getStats()[i].getBase_stat());
					c.append("\n");
				}

				pokeName.setText(b.toString());
				pokeType.setText(a.toString());
				pokeStats.setText(c.toString());
			}


			@Override
			public void onNetworkError(Throwable t) {
				Snackbar.make(findViewById(android.R.id.content), t.getMessage(), Snackbar.LENGTH_LONG).show();
			}

		};
		RetrofitFactory.getInstance().setPokemonNetworkListener(pokemonNetworkListener);
		RetrofitFactory.getInstance().getPokemon(pokemonName);
	}

	public void instantiateViews(){
		pokeImage = (ImageView) findViewById(R.id.detail_imageview_sprite);
		pokeName = (TextView) findViewById(R.id.detail_textview_name);
		pokeType = (TextView) findViewById(R.id.detail_textview_type);
		pokeStats = (TextView) findViewById(R.id.detail_textview_stats);
		showBack = (Button) findViewById(R.id.show_back);
		showBack.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				PokedexFragment frag = new PokedexFragment();
				android.support.v4.app.FragmentManager fragM = getSupportFragmentManager();
				FragmentTransaction trans = fragM.beginTransaction();
				Bundle bundle = new Bundle();
				bundle.putString("default","back");
				bundle.putString("pokemon",pokemonName);
				frag.setArguments(bundle);
				trans.replace(R.id.frag_container,frag);
				trans.commit();
			}
		});
		showFront = (Button) findViewById(R.id.show_front);
		showFront.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				PokedexFragment frag = new PokedexFragment();
				android.support.v4.app.FragmentManager fragM = getSupportFragmentManager();
				FragmentTransaction trans = fragM.beginTransaction();
				Bundle bundle = new Bundle();
				bundle.putString("default","front");
				bundle.putString("pokemon",pokemonName);
				frag.setArguments(bundle);
				trans.replace(R.id.frag_container,frag);
				trans.commit();

			}
		});

	}
}
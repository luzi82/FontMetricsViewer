package com.luzi82.fontmetricsviewer;

import android.app.Activity;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	FontDisplaySurfaceView mFontDisplaySurfaceView;
	TextView mTextView;
	Button mTypefaceButton;
	Button mHintingButton;
	Button mAlignButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mFontDisplaySurfaceView = (FontDisplaySurfaceView) findViewById(R.id.surfaceView1);
		mTextView = (TextView) findViewById(R.id.textView1);

		EditText editText = (EditText) findViewById(R.id.editText1);
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mFontDisplaySurfaceView.setText(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar1);
		seekBar.setMax(399);
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				int size = 1 + progress;
				// System.err.println("onProgressChanged: "+size);
				mFontDisplaySurfaceView.setTextSize(size);
				mTextView.setText(Integer.toString(size));
			}
		});

		mTypefaceButton = (Button) findViewById(R.id.button1);
		mTypefaceButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				++mTypefaceSelection;
				mTypefaceSelection %= TypefaceSelection.values().length;
				updateTypefaceSelection();
			}
		});

		mHintingButton = (Button) findViewById(R.id.buttonHints);
		mHintingButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				++mHintingSelection;
				mHintingSelection %= HintingSelection.values().length;
				updateHintingSelection();
			}
		});

		mAlignButton = (Button) findViewById(R.id.buttonAlign);
		mAlignButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				++mAlignSelection;
				mAlignSelection %= Paint.Align.values().length;
				updateAlignSelection();
			}
		});

		editText.setText(getString(R.string.app_name));
		seekBar.setProgress(63);
		updateTypefaceSelection();
		updateAlignSelection();
		updateHintingSelection();
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }

	// public interface FontSelection{
	// public String name();
	// public Typeface typeface();
	// }

	// public static final FontSelection[] TYPEFACE_LIST={
	// new FontSelection(){
	// @Override
	// public String name() {
	// return
	// }
	//
	// @Override
	// public Typeface typeface() {
	// return Typeface.DEFAULT;
	// }
	//
	// },
	// };

	int mAlignSelection = 0;

	public void updateAlignSelection() {
		Paint.Align align = Paint.Align.values()[mAlignSelection];
		mAlignButton.setText(align.name());
		mFontDisplaySurfaceView.setAlign(align);
	}

	public enum HintingSelection {

		OFF("HINTING_OFF", Paint.HINTING_OFF), ON("HINTING_ON", Paint.HINTING_ON), ;

		public final String name;
		public final int hinting;

		private HintingSelection(String name, int hinting) {
			this.name = name;
			this.hinting = hinting;
		}
	}

	int mHintingSelection = 0;

	public void updateHintingSelection() {
		HintingSelection s = HintingSelection.values()[mHintingSelection];
		mHintingButton.setText(s.name);
		mFontDisplaySurfaceView.setHinting(s.hinting);
	}

	public enum TypefaceSelection {

		DEFAULT("Typeface.DEFAULT", Typeface.DEFAULT), //
		DEFAULT_BOLD("Typeface.DEFAULT_BOLD", Typeface.DEFAULT_BOLD), //
		MONOSPACE("Typeface.MONOSPACE", Typeface.MONOSPACE), //
		SANS_SERIF("Typeface.SANS_SERIF", Typeface.SANS_SERIF), //
		SERIF("Typeface.SERIF", Typeface.SERIF), //
		;

		public final String name;
		public final Typeface typeface;

		private TypefaceSelection(String name, Typeface typeface) {
			this.name = name;
			this.typeface = typeface;
		}
	}

	int mTypefaceSelection = 0;

	public void updateTypefaceSelection() {
		TypefaceSelection ts = TypefaceSelection.values()[mTypefaceSelection];
		mTypefaceButton.setText(ts.name);
		mFontDisplaySurfaceView.setTextTypeface(ts.typeface);
	}

}

package view.gui.tuner;

import com.badlogic.gdx.utils.Align;

import engine.Loader;
import view.Group;
import view.gui.Button;
import view.gui.ButtonType;

public abstract class Tuner extends Group{
	private Setable setable;
	private Button plusBtn, minusBtn;
	private TunerListener listener;
	private TunerAddEvent plusEvent, minusEvent;
	public int value, min = Integer.MIN_VALUE, max = Integer.MAX_VALUE;
	
	public Tuner(Loader loader, float x, float y){
		plusBtn = new Button(loader, ButtonType.SMALL_TEXT, "+", null);
		minusBtn = new Button(loader, ButtonType.SMALL_TEXT, "-", null);
		minusBtn.setPosition(plusBtn.getRight() + 5, 0);
		
		plusEvent = new TunerAddEvent(this, 1);
		minusEvent = new TunerAddEvent(this, -1);
		
		plusBtn.addListener(plusEvent);
		minusBtn.addListener(minusEvent);
		
		setPosition(x, y);
		setSize(minusBtn.getRight(), minusBtn.getHeight());
		setOrigin(Align.center);
		
		addActor(plusBtn);
		addActor(minusBtn);
	}
	
	public void setFastAdjustment(boolean value){
		plusEvent.setFast(value);
		minusEvent.setFast(value);
	}
	
	public void setBounds(int min, int max){
		setMinBound(min);
		setMaxBound(max);
	}
	
	public void setMinBound(int value){
		min = value;
		if (this.value < min){
			this.value = min;
			valueChanged();
		}
	}
	
	public void setMaxBound(int value){
		max = value;
		if (this.value > max){
			this.value = max;
			valueChanged();
		}
	}
	
	protected void setSetable(Setable setable){
		this.setable = setable;
	}
	
	public void addListener(TunerListener listener){
		this.listener = listener;
	}
	
	private void valueChanged(){
		if (listener != null)
			listener.valueChanged(value);
		setable.setValue(value);
	}
	
	public void addValue(int amount){
		int newValue = value + amount;
		if (newValue < min || newValue > max)
			return;
		value = newValue;
		setable.setValue(value);
		valueChanged();
	}
	
	public void setStatValue(int value){
		this.value = value;
		setable.setValue(value);
	}
	
	public int getValue(){
		return value;
	}
}

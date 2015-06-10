package com.mygame.pure.utils;
class CharacterParse{
	public static final int E =1;//外向
	public static final int I =2;//内倾
	public static final int N =3;//理想
	public static final int S =4;//务实
	public static final int T =5;//理性
	public static final int F =6;//感性
	public static final int J =7;//计划
	public static final int P =8;//随性
	//性格大类
	private final int NT = 1;
	private final int NF = 2;
	private final int SJ = 3;
	private final int SP = 4;
	
	
	//做题之后得到的性格类型以及该性格的数量
	private int [] CharacterAndNum = new int [8+1];
	
	int [] CharacterType = new int [4+1];
	int CharacterbigType = 0;
	public int [] getCharacterAndNum() {
		return CharacterAndNum;
	}
	//每做一题都会调用此函数，用来统计性格
	public void setCharacterAndNum(int Character) {
		CharacterAndNum[Character] += 1;
	}
	//得出性格完整的性格类型
	private void getCharacterBigType(){
		CharacterType[1] = (CharacterAndNum[E] > CharacterAndNum[I])?E:I;
		CharacterType[2] = (CharacterAndNum[N] > CharacterAndNum[S])?N:S;
		CharacterType[3] = (CharacterAndNum[T] > CharacterAndNum[F])?T:F;
		CharacterType[4] = (CharacterAndNum[J] > CharacterAndNum[P])?J:P;
	}
	//获取性格大类
	public int getCharacterType(){
		getCharacterBigType();
		
		if(CharacterType[2] == N){
			if(CharacterType[3] == T){
				CharacterbigType = NT;
			}
			else if(CharacterType[3] == F)
			{
				CharacterbigType = NF;
			}
		}
		else if(CharacterType[2] == S)
		{
			if(CharacterType[4] == J){
				CharacterbigType = SJ;
			}
			else if(CharacterType[4] == P)
			{
				CharacterbigType = SP;
			}
		}
		return CharacterbigType;
	}
	
}
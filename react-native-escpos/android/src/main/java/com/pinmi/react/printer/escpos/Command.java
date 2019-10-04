package com.pinmi.react.printer.escpos;

public class Command {

	private static final byte ESC = 0x1B;
	private static final byte FS = 0x1C;
	private static final byte GS = 0x1D;
	private static final byte US = 0x1F;
	private static final byte DLE = 0x10;
	private static final byte DC4 = 0x14;
	private static final byte DC1 = 0x11;
	private static final byte SP = 0x20;
	private static final byte NL = 0x0A;
	private static final byte FF = 0x0C;
	public static final byte PIECE = (byte) 0xFF;
	public static final byte NUL = (byte) 0x00;
	
	//Inicialização da impressora
	public static byte[] ESC_Init = new byte[] {ESC, '@' };
	
	/**
	 * Ordem de impressão
	 */
	//Imprimir e embrulhar
	public static byte[] LF = new byte[] {NL};
	
	//Imprima e pegue papel
	public static byte[] ESC_J = new byte[] {ESC, 'J', 0x00 };
	public static byte[] ESC_d = new byte[] {ESC, 'd', 0x00 };
	
	//Imprimir uma página de autoteste
	public static byte[] US_vt_eot = new byte[] {US, DC1, 0x04 };
	
	 //Comando de bipe
    public static byte[] ESC_B_m_n = new byte[] {ESC, 'B', 0x00, 0x00 };
	
    //Instrução do cortador
    public static byte[] GS_V_n = new byte[] {GS, 'V', 0x00 };
    public static byte[] GS_V_m_n = new byte[] {GS, 'V', 'B', 0x00 };
    public static byte[] GS_i = new byte[] {ESC, 'i' };
    public static byte[] GS_m = new byte[] {ESC, 'm' };
	
	/**
	 * Comando de configuração de caracteres
	 */
	//Defina o espaçamento correto de caracteres
	public static byte[] ESC_SP = new byte[] {ESC, SP, 0x00 };
	
	//Definir formato de fonte de impressão de caracteres
	public static byte[] ESC_ExclamationMark = new byte[] {ESC, '!', 0x00 };
	
	//Defina a altura e a largura da fonte
	public static byte[] GS_ExclamationMark = new byte[] {GS, '!', 0x00 };
	
	//Definir impressão inversa
	public static byte[] GS_B = new byte[] {GS, 'B', 0x00 };
	
	//Cancelar / selecionar rotação de 90 graus
	public static byte[] ESC_V = new byte[] {ESC, 'V', 0x00 };
	
	//Selecionar fonte (principalmente código ASCII)
	public static byte[] ESC_M = new byte[] {ESC, 'M', 0x00 };
	
	//Selecionar / cancelar instruções em negrito
	public static byte[] ESC_G = new byte[] {ESC, 'G', 0x00 };
	public static byte[] ESC_E = new byte[] {ESC, 'E', 0x00 };
	
	//Selecionar / cancelar o modo de impressão invertida
	public static byte[] ESC_LeftBrace = new byte[] {ESC, '{', 0x00 };
	
	//Definir a altura do sublinhado (caractere)
	public static byte[] ESC_Minus = new byte[] {ESC, 45, 0x00 };
	
	//Modo de personagem
	public static byte[] FS_dot = new byte[] {FS, 46 };
	
	//Modo de caracteres chineses
	public static byte[] FS_and = new byte[] {FS, '&' };
	
	//Definir o modo de impressão de caracteres chineses
	public static byte[] FS_ExclamationMark = new byte[] {FS, '!', 0x00 };
	
	//Definir a altura do sublinhado (caracteres chineses)
	public static byte[] FS_Minus = new byte[] {FS, 45, 0x00 };
	
	//Defina o espaçamento esquerdo e direito dos caracteres chineses
	public static byte[] FS_S = new byte[] {FS, 'S', 0x00, 0x00 };
	
	//Selecionar página de código de caractere
	public static byte[] ESC_t = new byte[] {ESC, 't', 0x00 };
	
	/**
	 * Instrução de formatação
	 */
	//Definir espaçamento de linha padrão
	public static byte[] ESC_Two = new byte[] {ESC, 50}; 
	
	//Definir espaçamento entre linhas
	public static byte[] ESC_Three = new byte[] {ESC, 51, 0x00 };
	
	//Defina o modo de alinhamento
	public static byte[] ESC_Align = new byte[] {ESC, 'a', 0x00 };
	
	//Defina a margem esquerda
	public static byte[] GS_LeftSp = new byte[] {GS, 'L', 0x00 , 0x00 };
	
	//Definir posição absoluta de impressão
	//Defina a posição atual para o início da linha (nL + nH x 256).
	//Este comando será ignorado se a posição definida estiver fora da área de impressão especificada
	public static byte[] ESC_Absolute  = new byte[] {ESC, '$', 0x00, 0x00 };
	
	//Definir posição de impressão relativa
	public static byte[] ESC_Relative = new byte[] {ESC, 92, 0x00, 0x00 };
	
	//Defina a largura da área de impressão
	public static byte[] GS_W = new byte[] {GS, 'W', 0x00, 0x00 };

	/**
	 * Instrução de status
	 */
	//Instrução de transferência de status em tempo real
	public static byte[] DLE_eot = new byte[] {DLE, 0x04, 0x00 };
	
	//Instruções de caixa de dinheiro em tempo real
	public static byte[] DLE_DC4 = new byte[] {DLE, DC4, 0x00, 0x00, 0x00 };
	
	//Instruções de caixa padrão
	public static byte[] ESC_p = new byte[] {ESC, 'F', 0x00, 0x00, 0x00 };
	
	/**
	 * Instruções de configuração do código de barras
	 */
	//Selecione o método de impressão HRI
	public static byte[] GS_H = new byte[] {GS, 'H', 0x00 };
	
	//Defina a altura do código de barras
	public static byte[] GS_h = new byte[] {GS, 'h', (byte) 0xa2 };
	
	//Defina a largura do código de barras
	public static byte[] GS_w = new byte[] {GS, 'w', 0x00 };
	
	//Definir fonte da fonte de caracteres HRI
	public static byte[] GS_f = new byte[] {GS, 'f', 0x00 };
	
	//Instruções de deslocamento à esquerda do código de barras
	public static byte[] GS_x = new byte[] {GS, 'x', 0x00 };
	
	//Comando Imprimir código de barras
	public static byte[] GS_k = new byte[] {GS, 'k', 'A', FF };

	//Instruções relacionadas ao código QR		
    public static byte[] GS_k_m_v_r_nL_nH = new byte[] { ESC, 'Z', 0x03, 0x03, 0x08, 0x00, 0x00 };

	public static byte[] SELECT_BIT_IMAGE_MODE = {0x1B, 0x2A, 33};
}

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;



/**
 * Classe voltada ao tratamento/validacao da formula
 * 
 * 
 */
public class Tratamento {
    /**
     * Funcao que verifica a aparicao de uma nova proposicao, util na validacao visto que a formula so pode ter no maximo 5 prop. diferentes
     * @param vetor
     * @param caractere
     * 
     */
    public static boolean verificapropnova(char[] vetor, char caractere) {
        for (int i = 0; i < vetor.length; i++) {
            if (vetor[i] == caractere) {
                return false;
            }
        }
        return true;
    }
    /**
     * primeira funcao auxiliar para a validacao dos parenteses na expressao
     * @param oper
     * @param expressao
     * @param posoper
     * 
     */
    public static boolean verificar_par_oper_string(int[] oper,String expressao,int posoper){
	int temp,j,count;
	for(int i = 0; i<posoper; i++){ 
		temp = 0;
		if(oper[i] != -1){

			j = i + 1;
			while ((j<posoper) && (temp == 0)){
				if(oper[j] != -1){
					temp++;}
				else{
					j++;}
			}

			if(j>= posoper){
				return true;}
			if(temp != 0){
				count = 0;
				for(int k = oper[i]; k < oper[j]; k++){
					if((expressao.charAt(k) == ')') || (expressao.charAt(k) == '(')){
						count++;}
				}
				if(count == 0){
					return false;}
			}
		}
	}
	return true;

}

/**
 *  segunda funcao auxiliar para a validacao dos parenteses na expressao; difere da primeira por utilizar um vetor de caracteres no lugar de uma string
 * @param oper
 * @param expressao
 * @param posoper
 * 
 */
public static boolean verificarParOperVetorCaractere(int[] oper, char[] expressao, int posoper) {
    int temp, j, count;
    for (int i = 0; i < posoper; i++) {
        temp = 0;
        if (oper[i] != -1) {
            j = i + 1;
            while ((j < posoper) && (temp == 0)) {
                if (oper[j] != -1) {
                    temp++;
                } else {
                    j++;
                }
            }

            if (j >= posoper) {
                return true;
            }
            if (temp != 0) {
                count = 0;
                for (int k = oper[i]; k < oper[j]; k++) {
                    if ((expressao[k] == ')') || (expressao[k] == '(')) {
                        count++;
                    }
                }
                if (count == 0) {
                    return false;
                }
            }
        }
    }
    return true;
}



/**
 * funcao principal da classe, responsavel pela validacao da expressao
 * @param expressao
 * @throws Exception
 */
    public static void verificarformula(String expressao) throws Exception {
        String expressaocompacta = expressao.replace(" ", ""); 
        int n = expressaocompacta.length();        
        
        HashSet<Character> caps = new HashSet<>(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'));
        HashSet<Character> caracteres = new HashSet<>(Arrays.asList('+', '*', '~', '>', '(', ')'));
	    HashSet<Character> operadores = new HashSet<>(Arrays.asList('+', '*', '~', '>'));

        int countparaberto=0,countparfechado=0,countmai=0,countdupla=0,posoper=0,posprop=-1,aux = 0,dir,esq,x,pospar,count;
        int[] oper = new int[n];
        for (int i = 0; i < n; i++) {
            oper[i] = -1;
        }
        char[] prop = new char[5];
        for (int i = 0; i < 5; i++) {
            prop[i] = ' ';
        }

        //ate ai foi so declarando e criando coisas(contadores, HashSet's e vetores), a validacao começa agora

        if(expressaocompacta.charAt(0)!= '(' || expressaocompacta.charAt(n-1)!=')'){
            throw new Exception("formulainvalida-extremidadesemparenteses");}   //vai verificar se a formula eh do tipo (  ...  ), visto que foi padronizada para ser assim

        for (int i = 0; i < n; i++) {                           //laco que vai percorrer toda a formula compactada, ou seja, a formula sem " " espaços
            if( !caps.contains(expressaocompacta.charAt(i)) && !caracteres.contains(expressaocompacta.charAt(i))) {  
                throw new Exception("caractere invalido");}             // erro se tiver algum caractere imprevisto
            
            if(countdupla < 0){     //esse contador sera sempre no minimo 0
                countdupla = 0;}
            
            if(caps.contains(expressaocompacta.charAt(i))){
                countmai++;                     //contador de proposicoes
                countdupla++;                   //verificador de ocorrencia de duas proposicoes seguidas, algo como PQ PP ou ate P(Q
                if(verificapropnova(prop,expressaocompacta.charAt(i))){
                    posprop++;
                    if(posprop<5){
                        prop[posprop] = expressaocompacta.charAt(i);
                    }
                    else{
                        throw new Exception("proposicaoexcedente");}     //erro caso tenha mais de 5 proposicoes diferentes P Q R S T U por exemplo
                }
            }
            if(countdupla >=2){ //sinal de que ouve duas proposicoes seguidas PP PQ P((((Q
                throw new Exception("proposicaoinvalida");}

            if(operadores.contains(expressaocompacta.charAt(i))){
                countdupla--; //diminui o count dupla, pois teve a ocorrencia de um operador. Vale lembrar que nao considera parenteses
                oper[posoper] = i; //vetor guarda a posicao de cada operador da expressaocompacta
                posoper++;
                if(expressaocompacta.charAt(i) != '~'){
                    aux++;                  //aux sera o contador de operadores diferentes de ~   .Sera usado mais pra frente
                }
            }
            if( expressaocompacta.charAt(i) == '(' ){
                countparaberto++;
                if(expressaocompacta.charAt(i+1) == ')'){
                    throw new Exception("parentesesvazio");}            //puramente para evitar casos de () na expressao   
                if((i<=n-3)&&(caps.contains(expressaocompacta.charAt(i+1)))&&(expressaocompacta.charAt(i+2) == ')')){
                    throw new Exception("proposicaosemoperador");}      //evitar casos como (A), pois foi padronizado sempre ter um operador entre parenteses
                
            }
            if( expressaocompacta.charAt(i) == ')' ){
                countparfechado++;}

            if(countparfechado > countparaberto){   //verifica a ocorrencia de casos do tipo ) (,   ((... )))
                throw new Exception("parenteseaberto");}

        } //fim do laco inicial, garantimos de inicio que a string so contem caracteres validos e ja tratamos certos casos, como prop dupla, mais de 5 prop, ')' aberto
        if(countparaberto != countparfechado){
            throw new Exception("parenteseaberto");} //aqui garantimos que a expressao nao tenha parenteses abertos ((), enquanto no laço verificamos se nao tinha um ')' antes de um '('
        
        if(countmai == 0){
            throw new Exception("semproposicao");} //invalidamos casos em que nao ha proposicao
        
        if(countparaberto != posoper){     //aqui leva em conta a padronizacao que adotamos de ter um () para cada operador
            if (countparaberto < posoper){
                throw new Exception("operadorsemparenteses");} //casos do tipo (A+B)*C
            else{
                throw new Exception("parentesesexcedentes");} //casos do tipo ((A+B)) 
        }
        //lembrando que aux eh a quantidade de operadores + * > na formula, temos que
        if(aux != (countmai - 1)){         //esse eh muito importante, pois verificara se contem o numero certo de proposicoes de acordo com os operadores
            throw new Exception("formulainvalida");}   //vai dar erro em casos do tipo (A*C)not(B) e eh notavel essa condicao, visto que ~ nao contam no aux
        

        //agora comeca o tratamento de cada operador individualmente, evitar casos invalidos como A(*C  A+)B  A*(+B), e aprovar casos A+(B   )+ B    A + (~
        
        for (int i = 0; i < posoper; i++) {
            esq = oper[i] -1;
            dir = oper[i] +1;
            aux = 0;
            if(expressaocompacta.charAt(oper[i]) == '~'){                   //se for operador ~
                if(expressaocompacta.charAt(oper[i]-1) != '('){             //caso fure a padronizacao adotada: (~...)
                    throw new Exception("formulainvalida-not");
                }
                while((esq>0)&& (aux==0)){                                         //esq>0 pois ja padronizamos q a posicao 0 da formula vai ser '('
                    esq--;
                    if((expressaocompacta.charAt(esq)== '+')||(expressaocompacta.charAt(esq)== '*')||(expressaocompacta.charAt(esq)== '~')||(expressaocompacta.charAt(esq)== '>')){
                        aux=1; //vai sair do while pois esse not foi validado pela esquerda
                    }
                    if((caps.contains(expressaocompacta.charAt(esq)))||(expressaocompacta.charAt(esq)== ')')){
                        throw new Exception("formulainvalida-not"); //se detectar uma letra ou ')' antes de um operador vai dar erro, pois sao casos como A((~    )((~ q eh invalido
                    }
                }

                aux = 0;
                while((dir<n)&& (aux==0)){                            
                    if(dir == n-1){                              //vai ser o caso que o not nao foi validado pela direita ate o final, ex: ~(((()
                        throw new Exception("formulainvalida-not");
                    }
                    if((expressaocompacta.charAt(dir)== '~')||(caps.contains(expressaocompacta.charAt(dir)))){
                        aux=1; //vai sair do while pois esse not foi validado pela direita ~((~  ~A    ~((A
                    }
                    if((expressaocompacta.charAt(dir)== '+')||(expressaocompacta.charAt(dir)== '*')||(expressaocompacta.charAt(dir)== ')')||(expressaocompacta.charAt(dir)== '>')){
                        throw new Exception("formulainvalida-not"); //se detectar operador nao not ou ')' a direita invalida, pois sao casos como ~+    ~(>     ~)
                    }
                    dir++;
                } 
            } //aqui, tratamos o operador dessa posicao, caso fosse um not
            


            else{               //basta else, pois sabemos que eh operador nao not, e + * > funcionam igual nesse tratamento
                if(esq==0){
                    throw new Exception("formulainvalida-+*>");
                }
                while((esq>0)&& (aux==0)){
                    if(caps.contains(expressaocompacta.charAt(esq))){
                        aux=1;
                    }
                    if((operadores.contains(expressaocompacta.charAt(esq)))||(expressaocompacta.charAt(esq)== '(')){
                        throw new Exception("formulainvalida-+*>");
                    }
                    if((esq==1)&&(aux==0)){
                        throw new Exception("formulainvalida-+*>");
                    }
                    esq--;
                }
                aux = 0;


                if(dir==n-1){
                    throw new Exception("formulainvalida-+*>");
                }
                while((dir<n-1)&& (aux==0)){
                    if(caps.contains(expressaocompacta.charAt(dir))||(expressaocompacta.charAt(dir)== '~')){
                        aux=1;
                    }
                    if((expressaocompacta.charAt(dir)== '+')||(expressaocompacta.charAt(dir)== '*')||(expressaocompacta.charAt(dir)== ')')||(expressaocompacta.charAt(dir)== '>')){
                        throw new Exception("formulainvalida-+*>");
                    }
                    if((dir==n-2)&(aux==0)){
                        throw new Exception("formulainvalida-+*>");
                    }
                    dir++;

                }
            }
        }

        
        if(verificar_par_oper_string(oper,expressaocompacta,posoper) == false){
            throw new Exception("operador-parenteses invalido");
        }

        int[] opercopia = new int[oper.length];

        for (int i = 0; i < oper.length; i++) {
            opercopia[i] = oper[i];
        }


        char[] expressaocompactacopia = expressaocompacta.toCharArray();

        int[] par = new int[n];

        aux = 0;
        pospar = 0;
        x = 0;
        int p=0;
        while(p<n && aux==0){
	        count = 0;
	        if(expressaocompactacopia[p] == '('){
		        par[pospar] = p;
		        pospar++;
	        }

	        if(expressaocompactacopia[p] == ')'){
		        pospar--;
		        x = par[pospar];
		        for(int j = x; j<(p+1); j++){  //incluindo i no laço, ou seja, nao é x<i
			        if(operadores.contains(expressaocompactacopia[j])){
				        for(int i = 0; i<posoper; i++){
					        if(opercopia[i] == j){
						        opercopia[i] = -1;}
				        }
			        }				
			        expressaocompactacopia[j] = ' ';
		        }
            }
		    if((verificarParOperVetorCaractere(opercopia,expressaocompactacopia,posoper))== false){
			    throw new Exception("mais de um operador em um unico parenteses");}   // ou seja, algo tipo (~(A*B)*(~B))) em que o ~ e o * estao "no mesmo" parenteses
	        for(int l = 0; l<posoper; l++){
		        if(opercopia[l] != -1){
			        count++;}
	        }
	        if(count == 1){
		        aux =1;}
	        p++;
        }







        System.out.println(expressao + " valida");

    }
  }
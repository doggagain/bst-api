package hello;
public class BinarySearchTree<T extends HaveKey> {
	private BinaryNode<T> Root;
	private int Size;
	private BinaryNode<T> Median;
	
	public BinaryNode<T> GetMedian(){
		return this.Median;
	}
	
	public BinaryNode<T> GetRoot(){
		return this.Root;
	}
	
	public void SetRoot(BinaryNode<T> root){
		this.Root=root;
	}
	
	public int GetSize(){
		return this.Size;
	}
	
	public void IncrementSize(){
		this.Size++;
	}
	
	public void DecrementSize(){
		this.Size--;
	}
	
	public void Insert(BinaryNode<T> node){
		BinaryNode<T> x= this.Root;
		
		if(x==null){
			this.Root=node;
			this.Median=this.Root;
			this.Size=1;
			return;
		}
		
		while(!x.IsLeaf()){
			if(node.GetKey() < x.GetKey()){
				if(x.IsParentOfLeft()){
					x=x.GetLeft();
				}else{
					break;
				}
			}else{
				if(x.IsParentOfRight()){
					x=x.GetRight();
				}else{
					break;
				}
			}
		}
		
		node.SetParent(x);
		if(node.GetKey() < x.GetKey()){
			node.SetLeft(x.GetLeft());
			x.SetLeft(node);
			node.SetRight(x);
		}else{
			node.SetRight(x.GetRight());
			x.SetRight(node);
			node.SetLeft(x);
		}
		this.UpdateMedianInsert(node);
		this.IncrementSize();
	}
	
	
	
	public BinaryNode<T> Delete(T info){
		BinaryNode<T> searchResult= this.Search(info);
		if(searchResult==null){
			return null;
		}
		this.UpdateMedianDelete(searchResult);
		this.DecrementSize();
		
		BinaryNode<T> predecessor=searchResult.GetPredecessor();
		BinaryNode<T> successor=searchResult.GetSuccessor();
		
		BinaryNode<T> twoChildrenResult=ReassignIfHasTwoChildren(searchResult,predecessor,successor);
		if(twoChildrenResult!=null){
			return twoChildrenResult;
		}

		ReassignPredecessorSuccessor(searchResult,predecessor,successor);
		
		BinaryNode<T> leafResult=ReassignIfLeaf(searchResult,predecessor,successor);
		if(leafResult!=null){
			return leafResult;
		}
		
		return DeleteOneChild(searchResult);
	}
	
	public void ReassignPredecessorSuccessor(BinaryNode<T> searchResult,BinaryNode<T> predecessor,BinaryNode<T> successor){
		
		if( predecessor!=null && 
			predecessor.GetRight()==searchResult && 
			predecessor!=searchResult.GetParent() && 
			searchResult!=predecessor.GetParent()){
			predecessor.SetRight(successor);
		}
		
		if( successor!=null && 
			successor.GetLeft()==searchResult &&
			successor!=searchResult.GetParent()&&
			searchResult!=successor.GetParent()){
			successor.SetLeft(predecessor);
		}
	}
	
	public BinaryNode<T> ReassignIfLeaf(BinaryNode<T> searchResult,BinaryNode<T> predecessor,BinaryNode<T> successor){
		if(searchResult.IsLeaf()){
			
			if(searchResult.IsLeftChild()){
				
				searchResult.GetParent().SetLeft(predecessor);
				return searchResult;
			}else if(searchResult.IsRightChild()){
				
				searchResult.GetParent().SetRight(successor);
				return searchResult;
			}else{
				this.Root=null;
				return searchResult;
			}
			
		}
		return null;
	}
	
	public BinaryNode<T> ReassignIfHasTwoChildren(BinaryNode<T> searchResult,BinaryNode<T> predecessor,BinaryNode<T> successor){
		if(searchResult.IsParentOfLeft() && searchResult.IsParentOfRight()){
			
			BinaryNode<T> deleteResult=this.Delete(successor.GetInfo());
			
			T info = searchResult.GetInfo();
			searchResult.SetInfo(deleteResult.GetInfo());
			deleteResult.SetInfo(info);
			return deleteResult;
		}
		return null;
	}
	
	public BinaryNode<T> DeleteOneChild(BinaryNode<T> node){
		
		if(node.GetParent()==null){ //if for in case of root
			if(node.IsParentOfRight()){
				this.Root=node.GetRight();
			}else{
				this.Root=node.GetLeft();
			}
		}

		BinaryNode<T> child=null; //get your child
		if(node.IsParentOfLeft()){
			child=node.GetLeft();
		}else{
			child=node.GetRight();
		}

		if(node.IsLeftChild()){
			node.GetParent().SetLeft(child);
		}
		if(node.IsRightChild()){
			node.GetParent().SetRight(child);
		}
		child.SetParent(node.GetParent());
		return node;
	}
	
	public void UpdateMedianInsert(BinaryNode<T> node){
		boolean isEven=this.Size %2 ==0;
		boolean isSmaller = node.GetKey() < this.Median.GetKey();
		
		if(isEven && isSmaller){
			this.Median=this.Median.GetPredecessor();
		}
		
		if(!(isEven || isSmaller)){
			this.Median=this.Median.GetSuccessor();
		}
	}
	
	public void UpdateMedianDelete(BinaryNode<T> node){
		boolean isEven=this.Size %2 ==0;
		boolean isSmaller = node.GetKey() < this.Median.GetKey();
		
		if(!isEven && isSmaller){
			this.Median=this.Median.GetSuccessor();
		}
		
		if(isEven && !isSmaller){
			this.Median=this.Median.GetPredecessor();
		}
	}
	
	public BinaryNode<T> GetMinimum(){
		return this.Root.GetMinium();
	}
	
	public BinaryNode<T> GetMaximum(){
		return this.Root.GetMaximum();
	}
	
	public  BinaryNode<T> GetPredecessor(BinaryNode<T> node){
		return node.GetPredecessor();
	}
	
	public  BinaryNode<T> GetSuccessor(BinaryNode<T> node){
		return node.GetSuccessor();
	}
	
	public BinaryNode<T> Search(T info){
		BinaryNode<T> current=this.Root;
		while(!current.IsLeaf() && current.GetKey()!=info.GetKey()){
			if(info.GetKey()< current.GetKey()){
				
				if(current.IsParentOfLeft()){
					current=current.GetLeft();
				}else{
					return null;
				}
				
			}else{
				
				if(current.IsParentOfRight()){
					current=current.GetRight();
				}else{
					return null;
				}
			}
		}
		if(current.GetKey()==info.GetKey()){
			return current;
		}
		return null;
	}

	public String[] InorderStart(){
		String[] keys=new String[this.Size];
		int[] index={0};
		Inorder(this.Root,keys,index);
		return keys;
	}

	public void Inorder(BinaryNode<T> node,String[] keys,int[] index){
		if(node.IsParentOfLeft()){
			Inorder(node.GetLeft(),keys,index);
		}
		keys[index[0]]=String.valueOf(node.GetKey());
		index[0]++;
		if(node.IsParentOfRight()){
			Inorder(node.GetRight(),keys,index);
		}
	}

	
	public String[] PreorderStart(){
		String[] keys=new String[this.Size];
		int[] index={0};
		Preorder(this.Root,keys,index);
		return keys;
	}

	public void Preorder(BinaryNode<T> node,String[] keys,int[] index){
		keys[index[0]]=String.valueOf(node.GetKey());
		index[0]++;
		if(node.IsParentOfLeft()){
			Preorder(node.GetLeft(),keys,index);
		}
		if(node.IsParentOfRight()){
			Preorder(node.GetRight(),keys,index);
		}
	}

	
	public String[] PostorderStart(){
		String[] keys=new String[this.Size];
		int[] index={0};
		Postorder(this.Root,keys,index);
		return keys;
	}	

	public void Postorder(BinaryNode<T> node,String[] keys,int[] index){
		if(node.IsParentOfLeft()){
			Postorder(node.GetLeft(),keys,index);
		}
		if(node.IsParentOfRight()){
			Postorder(node.GetRight(),keys,index);
		}
		keys[index[0]]=String.valueOf(node.GetKey());
		index[0]++;
	}
}

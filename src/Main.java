public class Main {

    public static void main(String[] args) {
        int nums[] = {2,5,5,11};
        int target = 10;
        twoSum(nums,target);
    }

    public static int[] twoSum(int[] nums, int target) {
        int sum ;
        int[] a = new int[2];
        for(int i=0;i<nums.length-1;i++) {
            for(int j=i+1;j<nums.length;j++) {
                sum = nums[i]+nums[j];
                if(target==sum){
                    a[0] = i;
                    a[1] = j;
                }
            }
        }
        return a;
    }
}

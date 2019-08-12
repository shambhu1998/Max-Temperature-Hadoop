import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class MaxTemperature {
    public static class Map extends Mapper<LongWritable, Text, Text, MapWritable> {
        private MapWritable mw = new MapWritable();
        private Text cityKey = new Text();

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] data = line.split(" ");
            if(data.length == 3){
                mw.put(new Text("date"), new IntWritable(Integer.parseInt(data[0])));
                mw.put(new Text("temp"), new IntWritable(Integer.parseInt(data[2])));
                cityKey.set(data[1]);
            }
            context.write(cityKey, mw);
        }
    }

    public static class Reduce extends Reducer<Text, MapWritable, Text, Text> {

        public void reduce(Text key, Iterable<MapWritable> values, Context context)
                throws IOException, InterruptedException {
            int maxTemp = 0;
            int date = 0;
            int temp = 0;
            for (MapWritable value : values) {
                date = ((IntWritable)(value.get(new Text("date")))).get();
                temp = ((IntWritable)(value.get(new Text("temp")))).get();
                if (temp > maxTemp){
                    maxTemp = temp;
                }
            }

            context.write(new Text(String.valueOf(date)), new Text(" "+key + " " + temp));
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf, "MaxTemperature");
        job.setJarByClass(MaxTemperature.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(MapWritable.class);

        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);
    }

}

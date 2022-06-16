namespace ArtGenerator
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void youClickedMe(object sender, EventArgs e)
        {
            outputLabel.Text = "you clicked me";
        }
    }
}